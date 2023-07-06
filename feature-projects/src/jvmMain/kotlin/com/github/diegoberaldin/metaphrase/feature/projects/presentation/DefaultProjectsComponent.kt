package com.github.diegoberaldin.metaphrase.feature.projects.presentation

import androidx.compose.runtime.snapshotFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.utils.activeAsFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.OpenProjectUseCase
import com.github.diegoberaldin.metaphrase.feature.projects.list.presentation.ProjectListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultProjectsComponent(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<ProjectsComponent.ViewIntent, ProjectsComponent.UiState, ProjectsComponent.Effect> = DefaultMviModel(
        ProjectsComponent.UiState(),
    ),
    private val keyStore: TemporaryKeyStore,
    private val projectRepository: ProjectRepository,
    private val recentProjectRepository: RecentProjectRepository,
    private val openProjectUseCase: OpenProjectUseCase,
) : ProjectsComponent,
    MviModel<ProjectsComponent.ViewIntent, ProjectsComponent.UiState, ProjectsComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private val navigation = StackNavigation<ProjectsComponent.Config>()
    private lateinit var viewModelScope: CoroutineScope

    override val childStack = childStack(
        source = navigation,
        initialConfiguration = ProjectsComponent.Config.List,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                childStack.activeAsFlow<ProjectListComponent>().filterNotNull().flatMapLatest { it.effects }
                    .filterIsInstance<ProjectListComponent.Effect.ProjectSelected>()
                    .onEach { event ->
                        openProject(event.value.id)
                    }.launchIn(viewModelScope)
                childStack.activeAsFlow<TranslateComponent>(true, Duration.INFINITE).flatMapLatest {
                    it?.isEditing ?: snapshotFlow { false }
                }.onEach { value ->
                    mvi.updateState { it.copy(isEditing = value) }
                }.launchIn(viewModelScope)
                childStack.activeAsFlow<TranslateComponent>(true, Duration.INFINITE).flatMapLatest {
                    it?.currentLanguage ?: snapshotFlow { null }
                }.onEach { value ->
                    mvi.updateState { it.copy(currentLanguage = value) }
                }.launchIn(viewModelScope)
            }
            doOnStart {
                viewModelScope.launch(dispatchers.io) {
                    val lastOpenedProject = keyStore.get(KeyStoreKeys.LastOpenedProject, "")
                    val lastOpenedPath = recentProjectRepository.getByName(lastOpenedProject)?.path
                    if (lastOpenedPath != null) {
                        openProjectUseCase(lastOpenedPath)?.id?.also {
                            openProject(it)
                        }
                    }
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: ProjectsComponent.ViewIntent) {
        when (intent) {
            ProjectsComponent.ViewIntent.AddSegment -> addSegment()
            ProjectsComponent.ViewIntent.CloseCurrentProject -> closeCurrentProject()
            ProjectsComponent.ViewIntent.CopyBase -> copyBase()
            ProjectsComponent.ViewIntent.DeleteSegment -> deleteSegment()
            ProjectsComponent.ViewIntent.EndEditing -> endEditing()
            is ProjectsComponent.ViewIntent.Export -> export(path = intent.path, type = intent.type)
            is ProjectsComponent.ViewIntent.ExportTmx -> exportTmx(intent.path)
            ProjectsComponent.ViewIntent.GlobalSpellcheck -> globalSpellcheck()
            is ProjectsComponent.ViewIntent.Import -> import(path = intent.path, type = intent.type)
            ProjectsComponent.ViewIntent.InsertBestMatch -> insertBestMatch()
            ProjectsComponent.ViewIntent.MachineTranslationContributeTm -> machineTranslationContributeTm()
            ProjectsComponent.ViewIntent.MachineTranslationCopyTarget -> machineTranslationCopyTarget()
            ProjectsComponent.ViewIntent.MachineTranslationInsert -> machineTranslationInsert()
            ProjectsComponent.ViewIntent.MachineTranslationRetrieve -> machineTranslationRetrieve()
            ProjectsComponent.ViewIntent.MachineTranslationShare -> machineTranslationShare()
            ProjectsComponent.ViewIntent.MoveToNext -> moveToNext()
            ProjectsComponent.ViewIntent.MoveToPrevious -> moveToPrevious()
            is ProjectsComponent.ViewIntent.Open -> open(intent.projectId)
            is ProjectsComponent.ViewIntent.SaveCurrentProject -> saveCurrentProject(intent.path)
            ProjectsComponent.ViewIntent.SyncWithTm -> syncWithTm()
            ProjectsComponent.ViewIntent.ValidatePlaceholders -> validatePlaceholders()
        }
    }

    private fun open(projectId: Int) {
        when (childStack.value.active.configuration) {
            is ProjectsComponent.Config.Detail -> {
                viewModelScope.launch(dispatchers.io) {
                    val translateComponent = childStack.activeAsFlow<TranslateComponent>().firstOrNull()
                    if (translateComponent != null && translateComponent.projectId != projectId) {
                        val current = uiState.value.activeProject
                        if (current != null) {
                            projectRepository.delete(current)
                        }

                        val project = projectRepository.getById(projectId)
                        if (project != null) {
                            withContext(dispatchers.io) {
                                keyStore.save(KeyStoreKeys.LastOpenedProject, project.name)
                            }
                        }
                        mvi.updateState { it.copy(activeProject = project) }

                        translateComponent.projectId = projectId
                    }
                }
            }

            else -> {
                viewModelScope.launch(dispatchers.io) {
                    openProject(projectId)
                }
            }
        }
    }

    private suspend fun openProject(projectId: Int) {
        if (uiState.value.activeProject?.id == projectId) {
            return
        }

        val project = projectRepository.getById(projectId)
        if (project != null) {
            withContext(dispatchers.io) {
                keyStore.save(KeyStoreKeys.LastOpenedProject, project.name)
            }
        }
        mvi.updateState { it.copy(activeProject = project) }
        withContext(dispatchers.main) {
            navigation.push(ProjectsComponent.Config.Detail(projectId = projectId))
        }
    }

    private fun createChild(config: ProjectsComponent.Config, componentContext: ComponentContext): Any = when (config) {
        is ProjectsComponent.Config.List -> getByInjection<ProjectListComponent>(
            componentContext,
            coroutineContext,
        )

        is ProjectsComponent.Config.Detail -> getByInjection<TranslateComponent>(
            componentContext,
            coroutineContext,
        ).apply {
            projectId = config.projectId
        }
    }

    private fun closeCurrentProject() {
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.LastOpenedProject, "")
            val current = uiState.value.activeProject
            if (current != null) {
                projectRepository.delete(current)
            }
            projectRepository.setNeedsSaving(false)
            mvi.updateState { it.copy(activeProject = null) }
            withContext(dispatchers.main) {
                navigation.pop()
            }
        }
    }

    private fun saveCurrentProject(path: String) {
        viewModelScope.launch(dispatchers.io) {
            runCatching {
                childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.save(path = path)
            }
        }
    }

    private fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.import(
                path = path,
                type = type,
            )
        }
    }

    private fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.export(
                path = path,
                type = type,
            )
        }
    }

    private fun moveToPrevious() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.moveToPrevious()
        }
    }

    private fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.moveToNext()
        }
    }

    private fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.endEditing()
        }
    }

    private fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.copyBase()
        }
    }

    private fun addSegment() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.addSegment()
        }
    }

    private fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.deleteSegment()
        }
    }

    private fun exportTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.exportTmx(path = path)
        }
    }

    private fun syncWithTm() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.syncWithTm()
        }
    }

    private fun validatePlaceholders() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.validatePlaceholders()
        }
    }

    private fun insertBestMatch() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.insertBestMatch()
        }
    }

    private fun globalSpellcheck() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.globalSpellcheck()
        }
    }

    private fun machineTranslationRetrieve() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.machineTranslationRetrieve()
        }
    }

    private fun machineTranslationInsert() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.machineTranslationInsert()
        }
    }

    private fun machineTranslationCopyTarget() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.machineTranslationCopyTarget()
        }
    }

    private fun machineTranslationShare() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.machineTranslationShare()
        }
    }

    private fun machineTranslationContributeTm() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.machineTranslationContributeTm()
        }
    }
}
