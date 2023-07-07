package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultCreateProjectComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<CreateProjectComponent.Intent, CreateProjectComponent.UiState, CreateProjectComponent.Effect> = DefaultMviModel(
        CreateProjectComponent.UiState(),
    ),
    private val languageRepository: LanguageRepository,
    private val projectRepository: ProjectRepository,
    private val segmentRepository: SegmentRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : CreateProjectComponent,
    MviModel<CreateProjectComponent.Intent, CreateProjectComponent.UiState, CreateProjectComponent.Effect> by mvi,
    ComponentContext by componentContext {

    override var projectId: Int = 0
        set(value) {
            field = value
            load(projectId)
        }

    private lateinit var viewModelScope: CoroutineScope

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                if (projectId > 0) {
                    load(projectId)
                } else {
                    refreshAvailableLanguages()
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: CreateProjectComponent.Intent) {
        when (intent) {
            is CreateProjectComponent.Intent.AddLanguage -> addLanguage(intent.value)
            is CreateProjectComponent.Intent.RemoveLanguage -> removeLanguage(intent.value)
            is CreateProjectComponent.Intent.SetBaseLanguage -> setBaseLanguage(intent.value)
            is CreateProjectComponent.Intent.SetName -> setName(intent.value)
            CreateProjectComponent.Intent.Submit -> submit()
        }
    }

    private fun refreshAvailableLanguages() {
        val allLanguages = languageRepository.getDefaultLanguages().map { completeLanguage(it) }
        mvi.updateState {
            it.copy(availableLanguages = allLanguages - it.languages.toSet())
        }
    }

    private fun load(projectId: Int) {
        if (!this::viewModelScope.isInitialized) return
        viewModelScope.launch {
            val project = projectRepository.getById(projectId)
            mvi.updateState { it.copy(name = project?.name.orEmpty()) }
            val projectLanguages = languageRepository.getAll(projectId).map { completeLanguage(it) }
            mvi.updateState {
                it.copy(languages = projectLanguages)
            }
            refreshAvailableLanguages()
        }
    }

    private fun setName(value: String) {
        mvi.updateState { it.copy(name = value) }
    }

    private fun addLanguage(value: LanguageModel) {
        mvi.updateState {
            it.copy(languages = it.languages.let { oldList -> oldList + value.copy(isBase = oldList.isEmpty()) })
        }
        refreshAvailableLanguages()
    }

    private fun setBaseLanguage(value: LanguageModel) {
        mvi.updateState {
            it.copy(languages = it.languages.let { oldList -> oldList.map { lang -> lang.copy(isBase = lang.code == value.code) } })
        }
    }

    private fun removeLanguage(value: LanguageModel) {
        mvi.updateState {
            it.copy(
                languages = it.languages.let { oldList ->
                    val newList = oldList - value
                    if (value.isBase && newList.isNotEmpty()) {
                        // assigns a new base language
                        listOf(newList.first().copy(isBase = true)) + newList.subList(
                            fromIndex = 1,
                            toIndex = newList.size,
                        )
                    } else {
                        newList
                    }
                },
            )
        }
        refreshAvailableLanguages()
    }

    private fun submit() {
        mvi.updateState { it.copy(nameError = "") }
        mvi.updateState { it.copy(languagesError = "") }
        val name = uiState.value.name.trim()
        var valid = true
        if (name.isEmpty()) {
            mvi.updateState { it.copy(nameError = "message_missing_field".localized()) }
            valid = false
        }
        val languages = uiState.value.languages
        if (languages.isEmpty()) {
            mvi.updateState { it.copy(languagesError = "message_select_one_language".localized()) }
            valid = false
        }
        if (!valid) {
            return
        }
        viewModelScope.launch(dispatchers.io) {
            mvi.updateState { it.copy(isLoading = true) }
            val oldBaseLanguage: LanguageModel?
            val newProjectId = if (projectId == 0) {
                val project = ProjectModel(
                    name = name,
                )
                oldBaseLanguage = null
                projectRepository.create(project)
            } else {
                val project = projectRepository.getById(projectId)
                if (project != null) {
                    projectRepository.update(project.copy(name = name))
                }
                oldBaseLanguage = languageRepository.getBase(projectId)
                projectId
            }
            for (lang in languages) {
                val existing = languageRepository.getByCode(code = lang.code, projectId = newProjectId)
                if (existing != null) {
                    languageRepository.update(existing.copy(isBase = lang.isBase))
                } else {
                    val newLangId = languageRepository.create(model = lang, projectId = newProjectId)
                    if (oldBaseLanguage != null) {
                        // copies all old segments to the new language
                        val toInsertSegments = segmentRepository.getAll(oldBaseLanguage.id).filter {
                            // if the new language is the new base language then all should be copied
                            // otherwise only the translatable ones should be created
                            lang.isBase || it.translatable
                        }.map { it.copy(text = "") }
                        segmentRepository.createBatch(models = toInsertSegments, languageId = newLangId)
                    }
                }
            }
            // remove stale languages
            val projectLanguages = languageRepository.getAll(newProjectId)
            for (lang in projectLanguages) {
                if (languages.none { it.code == lang.code }) {
                    languageRepository.delete(model = lang)
                }
            }

            val newBaseLanguage = languageRepository.getBase(projectId)
            if (oldBaseLanguage != null && oldBaseLanguage.code != newBaseLanguage?.code) {
                // all untranslatable segments from the old base language must be removed
                val toDeleteSegments = segmentRepository.getUntranslatable(oldBaseLanguage.id)
                for (segment in toDeleteSegments) {
                    segmentRepository.delete(segment)
                }
            }

            mvi.updateState { it.copy(isLoading = false) }
            val isNew = oldBaseLanguage == null
            val res = if (isNew) newProjectId else null
            mvi.emitEffect(CreateProjectComponent.Effect.Done(res))
        }
    }
}
