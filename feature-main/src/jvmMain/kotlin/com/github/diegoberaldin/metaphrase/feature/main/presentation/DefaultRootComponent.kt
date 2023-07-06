package com.github.diegoberaldin.metaphrase.feature.main.presentation

import androidx.compose.runtime.snapshotFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnResume
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.utils.asFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ClearGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ExportGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ImportGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.OpenProjectUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ClearTmUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ImportTmxUseCase
import com.github.diegoberaldin.metaphrase.feature.intro.presentation.IntroComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation.SettingsComponent
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation.CreateProjectComponent
import com.github.diegoberaldin.metaphrase.feature.projects.presentation.ProjectsComponent
import com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation.StatisticsComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.awt.Desktop
import java.net.URI
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultRootComponent(
    componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<RootComponent.ViewIntent, RootComponent.UiState, RootComponent.Effect> = DefaultMviModel(
        RootComponent.UiState(),
    ),
    private val recentProjectRepository: RecentProjectRepository,
    private val projectRepository: ProjectRepository,
    private val importFromTmx: ImportTmxUseCase,
    private val clearTranslationMemory: ClearTmUseCase,
    private val importGlossaryTerms: ImportGlossaryUseCase,
    private val exportGlossaryTerms: ExportGlossaryUseCase,
    private val clearGlossaryTerms: ClearGlossaryUseCase,
    private val openProjectUseCase: OpenProjectUseCase,
    private val notificationCenter: NotificationCenter,
) : RootComponent,
    MviModel<RootComponent.ViewIntent, RootComponent.UiState, RootComponent.Effect> by mvi,
    ComponentContext by componentContext {

    companion object {
        const val KEY_MAIN_SLOT = "MainSlot"
        const val KEY_DIALOG_SLOT = "DialogSlot"
        const val MANUAL_URL = "https://diegoberaldin.github.io/MetaPhrase/user_manual/main"
    }

    private lateinit var viewModelScope: CoroutineScope

    private val mainNavigation = SlotNavigation<RootComponent.Config>()
    private val dialogNavigation = SlotNavigation<RootComponent.DialogConfig>()
    private var projectIdToOpen: Int? = null

    override val main: Value<ChildSlot<RootComponent.Config, *>> = childSlot(
        source = mainNavigation,
        key = KEY_MAIN_SLOT,
        childFactory = ::createMainChild,
    )
    override val dialog: Value<ChildSlot<RootComponent.DialogConfig, *>> = childSlot(
        source = dialogNavigation,
        key = KEY_DIALOG_SLOT,
        childFactory = ::createDialogChild,
    )

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())

                main.asFlow<ProjectsComponent>(true, timeout = Duration.INFINITE)
                    .flatMapLatest { it?.uiState ?: snapshotFlow { ProjectsComponent.UiState() } }
                    .onEach { projectsUiState ->
                        mvi.updateState {
                            it.copy(
                                isEditing = projectsUiState.isEditing,
                                activeProject = projectsUiState.activeProject,
                                currentLanguage = projectsUiState.currentLanguage,
                            )
                        }
                    }.launchIn(viewModelScope)

                // initial cleanup
                viewModelScope.launch {
                    projectRepository.deleteAll()
                }
                viewModelScope.launch {
                    launch {
                        notificationCenter.events.filter { it is NotificationCenter.Event.ShowProgress }.onEach { evt ->
                            when (evt) {
                                is NotificationCenter.Event.ShowProgress -> {
                                    mvi.updateState { it.copy(isLoading = evt.visible) }
                                }

                                else -> Unit
                            }
                        }.launchIn(this)
                    }
                    launch {
                        main.asFlow<ProjectsComponent>(timeout = Duration.INFINITE).onEach { child ->
                            val projectId = projectIdToOpen
                            if (child != null && projectId != null) {
                                child.reduce(ProjectsComponent.ViewIntent.Open(projectId))
                                projectIdToOpen = null
                            }
                        }.launchIn(this)
                    }
                    launch {
                        projectRepository.observeNeedsSaving().onEach { needsSaving ->
                            mvi.updateState { it.copy(isSaveEnabled = needsSaving) }
                        }.launchIn(this)
                    }
                }
            }
            doOnResume {
                viewModelScope.launch(dispatchers.io) {
                    launch {
                        recentProjectRepository.observeAll().onEach { projects ->
                            withContext(dispatchers.main) {
                                if (projects.isEmpty()) {
                                    mainNavigation.activate(RootComponent.Config.Intro)
                                } else {
                                    mainNavigation.activate(RootComponent.Config.Projects)
                                }
                            }
                        }.launchIn(this)
                    }
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun hasUnsavedChanges(): Boolean = projectRepository.isNeedsSaving()

    override fun reduce(intent: RootComponent.ViewIntent) {
        when (intent) {
            RootComponent.ViewIntent.AddSegment -> addSegment()
            RootComponent.ViewIntent.ClearGlossary -> clearGlossary()
            RootComponent.ViewIntent.ClearTm -> clearTm()
            is RootComponent.ViewIntent.CloseCurrentProject -> closeCurrentProject(intent.closeAfter)
            RootComponent.ViewIntent.CloseDialog -> closeDialog()
            is RootComponent.ViewIntent.ConfirmCloseCurrentProject -> confirmCloseCurrentProject(
                openAfter = intent.openAfter,
                newAfter = intent.newAfter,
            )

            RootComponent.ViewIntent.CopyBase -> copyBase()
            RootComponent.ViewIntent.DeleteSegment -> deleteSegment()
            RootComponent.ViewIntent.EndEditing -> endEditing()
            is RootComponent.ViewIntent.Export -> export(path = intent.path, type = intent.type)
            is RootComponent.ViewIntent.ExportGlossary -> exportGlossary(intent.path)
            is RootComponent.ViewIntent.ExportTmx -> exportTmx(intent.path)
            RootComponent.ViewIntent.GlobalSpellcheck -> globalSpellcheck()
            is RootComponent.ViewIntent.Import -> import(path = intent.path, type = intent.type)
            is RootComponent.ViewIntent.ImportGlossary -> importGlossary(intent.path)
            is RootComponent.ViewIntent.ImportTmx -> importTmx(intent.path)
            RootComponent.ViewIntent.InsertBestMatch -> insertBestMatch()
            RootComponent.ViewIntent.MachineTranslationContributeTm -> machineTranslationContributeTm()
            RootComponent.ViewIntent.MachineTranslationCopyTarget -> machineTranslationCopyTarget()
            RootComponent.ViewIntent.MachineTranslationInsert -> machineTranslationInsert()
            RootComponent.ViewIntent.MachineTranslationRetrieve -> machineTranslationRetrieve()
            RootComponent.ViewIntent.MachineTranslationShare -> machineTranslationShare()
            RootComponent.ViewIntent.MoveToNextSegment -> moveToNextSegment()
            RootComponent.ViewIntent.MoveToPreviousSegment -> moveToPreviousSegment()
            RootComponent.ViewIntent.OpenDialog -> openDialog()
            RootComponent.ViewIntent.OpenEditProject -> openEditProject()
            is RootComponent.ViewIntent.OpenExportDialog -> openExportDialog(intent.type)
            RootComponent.ViewIntent.OpenExportGlossaryDialog -> openExportGlossaryDialog()
            RootComponent.ViewIntent.OpenExportTmxDialog -> openExportTmxDialog()
            is RootComponent.ViewIntent.OpenImportDialog -> openImportDialog(intent.type)
            RootComponent.ViewIntent.OpenImportGlossaryDialog -> openImportGlossaryDialog()
            RootComponent.ViewIntent.OpenImportTmxDialog -> openImportTmxDialog()
            RootComponent.ViewIntent.OpenManual -> openManual()
            RootComponent.ViewIntent.OpenNewDialog -> openNewDialog()
            is RootComponent.ViewIntent.OpenProject -> openProject(intent.path)
            RootComponent.ViewIntent.OpenSettings -> openSettings()
            RootComponent.ViewIntent.OpenStatistics -> openStatistics()
            RootComponent.ViewIntent.SaveCurrentProject -> saveCurrentProject()
            is RootComponent.ViewIntent.SaveProject -> saveProject(intent.path)
            RootComponent.ViewIntent.SaveProjectAs -> saveProjectAs()
            RootComponent.ViewIntent.SyncTm -> syncTm()
            RootComponent.ViewIntent.ValidatePlaceholders -> validatePlaceholders()
        }
    }

    private fun createMainChild(config: RootComponent.Config, componentContext: ComponentContext): Any = when (config) {
        RootComponent.Config.Projects -> getByInjection<ProjectsComponent>(componentContext, coroutineContext)
        else -> getByInjection<IntroComponent>(componentContext, coroutineContext)
    }

    private fun createDialogChild(config: RootComponent.DialogConfig, componentContext: ComponentContext): Any =
        when (config) {
            RootComponent.DialogConfig.NewDialog -> {
                getByInjection<CreateProjectComponent>(componentContext, coroutineContext).apply {
                    effects.filterIsInstance<CreateProjectComponent.Effect.Done>().onEach { event ->
                        withContext(dispatchers.main) {
                            closeDialog()
                        }
                        val projectId = event.projectId
                        if (projectId != null) {
                            projectRepository.setNeedsSaving(true)

                            when (val child = main.asFlow<Any>().firstOrNull()) {
                                is ProjectsComponent -> {
                                    child.reduce(ProjectsComponent.ViewIntent.Open(projectId))
                                }

                                is IntroComponent -> {
                                    projectIdToOpen = projectId

                                    withContext(dispatchers.main) {
                                        mainNavigation.activate(RootComponent.Config.Projects)
                                    }
                                }
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is RootComponent.DialogConfig.EditDialog -> {
                getByInjection<CreateProjectComponent>(componentContext, coroutineContext).apply {
                    projectId = this@DefaultRootComponent.uiState.value.activeProject?.id ?: 0
                    effects.filterIsInstance<CreateProjectComponent.Effect.Done>().onEach {
                        withContext(dispatchers.main) {
                            closeDialog()
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is RootComponent.DialogConfig.StatisticsDialog -> {
                getByInjection<StatisticsComponent>(componentContext, coroutineContext).apply {
                    projectId = this@DefaultRootComponent.uiState.value.activeProject?.id ?: 0
                }
            }

            is RootComponent.DialogConfig.SettingsDialog -> {
                getByInjection<SettingsComponent>(componentContext, coroutineContext)
            }

            else -> Unit
        }

    private fun openProject(path: String) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val project = openProjectUseCase(path = path)
            if (project != null) {
                val existing = recentProjectRepository.getByName(project.name)
                if (existing == null) {
                    recentProjectRepository.create(model = RecentProjectModel(name = project.name, path = path))
                }
                projectIdToOpen = project.id
                viewModelScope.launch(dispatchers.main) {
                    mainNavigation.activate(RootComponent.Config.Projects)
                }
            }
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    private fun openEditProject() {
        val projectId = uiState.value.activeProject?.id
        if (projectId != null) {
            viewModelScope.launch(dispatchers.main) {
                dialogNavigation.activate(RootComponent.DialogConfig.EditDialog)
            }
        }
    }

    private fun saveProjectAs() {
        val name = uiState.value.activeProject?.name
        if (name != null) {
            viewModelScope.launch(dispatchers.main) {
                dialogNavigation.activate(RootComponent.DialogConfig.SaveAsDialog(name = name))
            }
        }
    }

    private fun saveProject(path: String) {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()
                ?.reduce(ProjectsComponent.ViewIntent.SaveCurrentProject(path = path))
        }
    }

    private fun saveCurrentProject() {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val recentProjectModel = recentProjectRepository.getByName(uiState.value.activeProject?.name.orEmpty())
            if (recentProjectModel != null) {
                val path = recentProjectModel.path
                main.asFlow<ProjectsComponent>().firstOrNull()
                    ?.reduce(ProjectsComponent.ViewIntent.SaveCurrentProject(path = path))
            } else {
                saveProjectAs()
            }
        }
    }

    private fun openDialog() {
        viewModelScope.launch(dispatchers.main) {
            if (projectRepository.isNeedsSaving()) {
                viewModelScope.launch(dispatchers.main) {
                    dialogNavigation.activate(RootComponent.DialogConfig.ConfirmCloseDialog(openAfter = true))
                }
                return@launch
            }
            dialogNavigation.activate(RootComponent.DialogConfig.OpenDialog)
        }
    }

    private fun openNewDialog() {
        viewModelScope.launch(dispatchers.main) {
            if (projectRepository.isNeedsSaving()) {
                viewModelScope.launch(dispatchers.main) {
                    dialogNavigation.activate(RootComponent.DialogConfig.ConfirmCloseDialog(newAfter = true))
                }
                return@launch
            }
            dialogNavigation.activate(RootComponent.DialogConfig.NewDialog)
        }
    }

    private fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.None)
        }
    }

    private fun closeCurrentProject(closeAfter: Boolean) {
        if (projectRepository.isNeedsSaving()) {
            viewModelScope.launch(dispatchers.main) {
                dialogNavigation.activate(RootComponent.DialogConfig.ConfirmCloseDialog(closeAfter = closeAfter))
            }
        } else {
            confirmCloseCurrentProject(openAfter = false, newAfter = false)
        }
    }

    private fun confirmCloseCurrentProject(openAfter: Boolean, newAfter: Boolean) {
        projectIdToOpen = null
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.CloseCurrentProject)
            projectRepository.setNeedsSaving(false)

            // if no more projects, goes back to intro
            val projects = recentProjectRepository.getAll()
            if (projects.isEmpty()) {
                withContext(dispatchers.main) {
                    mainNavigation.activate(RootComponent.Config.Intro)
                }
            }

            when {
                newAfter -> openNewDialog()
                openAfter -> openDialog()
            }
        }
    }

    private fun openImportDialog(type: ResourceFileType) {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.ImportDialog(type))
        }
    }

    private fun openExportDialog(type: ResourceFileType) {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.ExportDialog(type))
        }
    }

    private fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(
                ProjectsComponent.ViewIntent.Import(
                    path = path,
                    type = type,
                ),
            )
        }
    }

    private fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(
                ProjectsComponent.ViewIntent.Export(
                    path = path,
                    type = type,
                ),
            )
        }
    }

    private fun moveToPreviousSegment() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.MoveToPrevious)
        }
    }

    private fun moveToNextSegment() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.MoveToNext)
        }
    }

    private fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.EndEditing)
        }
    }

    private fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.CopyBase)
        }
    }

    private fun addSegment() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.AddSegment)
        }
    }

    private fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.DeleteSegment)
        }
    }

    private fun openStatistics() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.StatisticsDialog)
        }
    }

    private fun openSettings() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.SettingsDialog)
        }
    }

    private fun openExportTmxDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.ExportTmxDialog)
        }
    }

    private fun exportTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.ExportTmx(path = path))
        }
    }

    private fun openImportTmxDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.ImportTmxDialog)
        }
    }

    private fun importTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            importFromTmx(path = path)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    private fun clearTm() {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            clearTranslationMemory()
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    private fun syncTm() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.SyncWithTm)
        }
    }

    private fun validatePlaceholders() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.ValidatePlaceholders)
        }
    }

    private fun insertBestMatch() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.InsertBestMatch)
        }
    }

    private fun globalSpellcheck() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.GlobalSpellcheck)
        }
    }

    private fun openImportGlossaryDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.ImportGlossaryDialog)
        }
    }

    private fun importGlossary(path: String) {
        viewModelScope.launch(dispatchers.io) {
            importGlossaryTerms(path)
        }
    }

    private fun openExportGlossaryDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(RootComponent.DialogConfig.ExportGlossaryDialog)
        }
    }

    private fun exportGlossary(path: String) {
        viewModelScope.launch(dispatchers.io) {
            exportGlossaryTerms(path = path)
        }
    }

    private fun clearGlossary() {
        viewModelScope.launch(dispatchers.io) {
            clearGlossaryTerms()
        }
    }

    private fun machineTranslationRetrieve() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()
                ?.reduce(ProjectsComponent.ViewIntent.MachineTranslationRetrieve)
        }
    }

    private fun machineTranslationInsert() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()
                ?.reduce(ProjectsComponent.ViewIntent.MachineTranslationInsert)
        }
    }

    private fun machineTranslationCopyTarget() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()
                ?.reduce(ProjectsComponent.ViewIntent.MachineTranslationCopyTarget)
        }
    }

    private fun machineTranslationShare() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.reduce(ProjectsComponent.ViewIntent.MachineTranslationShare)
        }
    }

    private fun machineTranslationContributeTm() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()
                ?.reduce(ProjectsComponent.ViewIntent.MachineTranslationContributeTm)
        }
    }

    private fun openManual() {
        runCatching {
            openInBrowser(MANUAL_URL)
        }.exceptionOrNull()?.also {
            it.printStackTrace()
        }
    }

    private fun openInBrowser(uri: String) {
        val osName by lazy(LazyThreadSafetyMode.NONE) {
            System.getProperty("os.name").lowercase(Locale.getDefault())
        }
        val desktop = Desktop.getDesktop()
        when {
            Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE) -> desktop.browse(URI(uri))
            "mac" in osName -> Runtime.getRuntime().exec(arrayOf("open", uri))
            "nix" in osName || "nux" in osName -> Runtime.getRuntime().exec(arrayOf("xdg-open", uri))
            else -> throw RuntimeException("cannot open $uri")
        }
    }
}
