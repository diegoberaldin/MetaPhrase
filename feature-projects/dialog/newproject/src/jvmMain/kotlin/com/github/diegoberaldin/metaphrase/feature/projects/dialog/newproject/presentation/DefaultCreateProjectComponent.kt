package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultCreateProjectComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val projectRepository: ProjectRepository,
    private val segmentRepository: SegmentRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : CreateProjectComponent, ComponentContext by componentContext {

    override var projectId: Int = 0
        set(value) {
            field = value
            load(projectId)
        }

    private lateinit var viewModelScope: CoroutineScope

    override val uiState = MutableStateFlow(CreateProjectUiState())
    override val done = MutableSharedFlow<Int?>()

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

    private fun refreshAvailableLanguages() {
        val allLanguages = languageRepository.getDefaultLanguages().map { completeLanguage(it) }
        uiState.update {
            it.copy(availableLanguages = allLanguages - it.languages.toSet())
        }
    }

    private fun load(projectId: Int) {
        if (!this::viewModelScope.isInitialized) return
        viewModelScope.launch {
            val project = projectRepository.getById(projectId)
            uiState.update { it.copy(name = project?.name.orEmpty()) }
            val projectLanguages = languageRepository.getAll(projectId).map { completeLanguage(it) }
            uiState.update {
                it.copy(languages = projectLanguages)
            }
            refreshAvailableLanguages()
        }
    }

    override fun setName(value: String) {
        uiState.update { it.copy(name = value) }
    }

    override fun addLanguage(value: LanguageModel) {
        uiState.update {
            it.copy(languages = it.languages.let { oldList -> oldList + value.copy(isBase = oldList.isEmpty()) })
        }
        refreshAvailableLanguages()
    }

    override fun setBaseLanguage(value: LanguageModel) {
        uiState.update {
            it.copy(languages = it.languages.let { oldList -> oldList.map { lang -> lang.copy(isBase = lang.code == value.code) } })
        }
    }

    override fun removeLanguage(value: LanguageModel) {
        uiState.update {
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

    override fun submit() {
        uiState.update { it.copy(nameError = "") }
        uiState.update { it.copy(languagesError = "") }
        val name = uiState.value.name.trim()
        var valid = true
        if (name.isEmpty()) {
            uiState.update { it.copy(nameError = "message_missing_field".localized()) }
            valid = false
        }
        val languages = uiState.value.languages
        if (languages.isEmpty()) {
            uiState.update { it.copy(languagesError = "message_select_one_language".localized()) }
            valid = false
        }
        if (!valid) {
            return
        }
        viewModelScope.launch(dispatchers.io) {
            uiState.update { it.copy(isLoading = true) }
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

            uiState.update { it.copy(isLoading = false) }
            val isNew = oldBaseLanguage == null
            done.emit(if (isNew) newProjectId else null)
        }
    }
}
