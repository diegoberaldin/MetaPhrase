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
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.utils.activeAsFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.feature.projects.list.presentation.ProjectListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultProjectsComponent(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val keyStore: TemporaryKeyStore,
    private val projectRepository: ProjectRepository,
) : ProjectsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ProjectsComponent.Config>()
    private lateinit var viewModelScope: CoroutineScope

    override val childStack = childStack(
        source = navigation,
        initialConfiguration = ProjectsComponent.Config.List,
        handleBackButton = true,
        childFactory = ::createChild,
    )
    override val activeProject = MutableStateFlow<ProjectModel?>(null)
    override lateinit var isEditing: StateFlow<Boolean>
    override lateinit var currentLanguage: StateFlow<LanguageModel?>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                childStack.activeAsFlow<ProjectListComponent>().filterNotNull().flatMapLatest { it.projectSelected }
                    .onEach { project ->
                        openProject(project.id)
                    }.launchIn(viewModelScope)
                isEditing = childStack.activeAsFlow<TranslateComponent>(true, Duration.INFINITE).flatMapLatest {
                    it?.isEditing ?: snapshotFlow { false }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = false,
                )
                currentLanguage = childStack.activeAsFlow<TranslateComponent>(true, Duration.INFINITE).flatMapLatest {
                    it?.currentLanguage ?: snapshotFlow { null }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = null,
                )
            }
            doOnStart {
                viewModelScope.launch(dispatchers.io) {
                    val lastOpenedProjectId = keyStore.get("lastOpenedProject", 0)
                    if (lastOpenedProjectId > 0) {
                        openProject(lastOpenedProjectId)
                    }
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun open(projectId: Int) {
        when (val conf = childStack.value.active.configuration) {
            is ProjectsComponent.Config.Detail -> {
                val childComp = (childStack.value.active.instance as TranslateComponent)
                if (childComp.projectId != conf.projectId) {
                    childComp.projectId = conf.projectId
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
        if (activeProject.value?.id == projectId) {
            return
        }

        withContext(dispatchers.io) {
            keyStore.save("lastOpenedProject", projectId)
        }
        activeProject.value = projectRepository.getById(projectId)
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

    override fun closeCurrentProject() {
        viewModelScope.launch(dispatchers.io) {
            keyStore.save("lastOpenedProject", 0)
        }
        activeProject.value = null
        navigation.pop()
    }

    override fun saveCurrentProject(path: String) {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.save(path = path)
        }
    }

    override fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.import(
                path = path,
                type = type,
            )
        }
    }

    override fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.export(
                path = path,
                type = type,
            )
        }
    }

    override fun moveToPrevious() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.moveToPrevious()
        }
    }

    override fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.moveToNext()
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.endEditing()
        }
    }

    override fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.copyBase()
        }
    }

    override fun addSegment() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.addSegment()
        }
    }

    override fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.deleteSegment()
        }
    }

    override fun exportTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.exportTmx(path = path)
        }
    }

    override fun syncWithTm() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.syncWithTm()
        }
    }

    override fun validatePlaceholders() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.validatePlaceholders()
        }
    }

    override fun insertBestMatch() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.insertBestMatch()
        }
    }

    override fun globalSpellcheck() {
        viewModelScope.launch(dispatchers.io) {
            childStack.activeAsFlow<TranslateComponent>().firstOrNull()?.globalSpellcheck()
        }
    }
}
