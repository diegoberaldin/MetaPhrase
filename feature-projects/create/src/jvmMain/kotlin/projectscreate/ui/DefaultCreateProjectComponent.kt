package projectscreate.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import data.LanguageModel
import data.ProjectModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import language.repo.LanguageRepository
import language.usecase.GetCompleteLanguageUseCase
import repository.local.ProjectRepository
import repository.local.SegmentRepository
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

    private val name = MutableStateFlow("")
    private val nameError = MutableStateFlow("")
    private val availableLanguages = MutableStateFlow<List<LanguageModel>>(emptyList())
    private val languages = MutableStateFlow<List<LanguageModel>>(emptyList())
    private val languagesError = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<CreateProjectUiState>
    override lateinit var languagesUiState: StateFlow<CreateProjectLanguagesUiState>
    override val done = MutableSharedFlow<Int?>()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    name,
                    nameError,
                    isLoading,
                ) { name, nameError, isLoading ->
                    CreateProjectUiState(
                        name = name,
                        nameError = nameError,
                        isLoading = isLoading,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = CreateProjectUiState(),
                )
                languagesUiState = combine(
                    availableLanguages,
                    languages,
                    languagesError,
                ) { availableLanguages, languages, languagesError ->
                    CreateProjectLanguagesUiState(
                        availableLanguages = availableLanguages,
                        languages = languages,
                        languagesError = languagesError,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = CreateProjectLanguagesUiState(),
                )
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
        availableLanguages.value = allLanguages - languages.value.toSet()
    }

    private fun load(projectId: Int) {
        if (!this::viewModelScope.isInitialized) return
        viewModelScope.launch {
            val project = projectRepository.getById(projectId)
            name.value = project?.name.orEmpty()
            val projectLanguages = languageRepository.getAll(projectId).map { completeLanguage(it) }
            languages.value = projectLanguages
            refreshAvailableLanguages()
        }
    }

    override fun setName(value: String) {
        name.value = value
    }

    override fun addLanguage(value: LanguageModel) {
        languages.getAndUpdate {
            it + value.copy(isBase = it.isEmpty())
        }
        refreshAvailableLanguages()
    }

    override fun setBaseLanguage(value: LanguageModel) {
        languages.getAndUpdate { oldList ->
            oldList.map { lang -> lang.copy(isBase = lang.code == value.code) }
        }
    }

    override fun removeLanguage(value: LanguageModel) {
        languages.getAndUpdate {
            val newList = it - value
            if (value.isBase && newList.isNotEmpty()) {
                // assigns a new base language
                listOf(newList.first().copy(isBase = true)) + newList.subList(fromIndex = 1, toIndex = newList.size)
            } else {
                newList
            }
        }
        refreshAvailableLanguages()
    }

    override fun submit() {
        nameError.value = ""
        languagesError.value = ""
        val name = name.value.trim()
        var valid = true
        if (name.isEmpty()) {
            nameError.value = "Please select a project name"
            valid = false
        }
        val languages = languages.value
        if (languages.isEmpty()) {
            languagesError.value = "Please select at least one language"
            valid = false
        }
        if (!valid) {
            return
        }
        viewModelScope.launch(dispatchers.io) {
            isLoading.value = true
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

            isLoading.value = false
            val isNew = oldBaseLanguage == null
            done.emit(if (isNew) newProjectId else null)
        }
    }
}
