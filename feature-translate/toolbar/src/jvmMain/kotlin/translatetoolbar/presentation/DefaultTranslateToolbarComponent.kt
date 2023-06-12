package translatetoolbar.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import common.utils.combine
import data.LanguageModel
import data.TranslationUnitTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import projectrepository.LanguageRepository
import projectusecase.GetCompleteLanguageUseCase
import kotlin.coroutines.CoroutineContext

internal class DefaultTranslateToolbarComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : TranslateToolbarComponent, ComponentContext by componentContext {

    override var projectId: Int = 0
        set(value) {
            field = value
            loadLanguages()
        }
    override val currentLanguage = MutableStateFlow<LanguageModel?>(null)
    private val currentTypeFilter = MutableStateFlow(TranslationUnitTypeFilter.ALL)
    private val availableLanguages = MutableStateFlow<List<LanguageModel>>(emptyList())
    private val availableFilters = MutableStateFlow(
        listOf(
            TranslationUnitTypeFilter.ALL,
            TranslationUnitTypeFilter.TRANSLATABLE,
            TranslationUnitTypeFilter.UNTRANSLATED,
        ),
    )
    private val currentSearch = MutableStateFlow("")
    private val isEditing = MutableStateFlow(false)
    private lateinit var viewModelScope: CoroutineScope
    private var _events = MutableSharedFlow<TranslateToolbarComponent.Events>()

    override lateinit var uiState: StateFlow<TranslateToolbarUiState>

    override val events: SharedFlow<TranslateToolbarComponent.Events> = _events.asSharedFlow()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    currentLanguage,
                    currentTypeFilter,
                    availableLanguages,
                    availableFilters,
                    currentSearch,
                    isEditing,
                ) { currentLanguage, currentTypeFilter, availableLanguages, availableFilters, currentSearch, isEditing ->
                    TranslateToolbarUiState(
                        currentLanguage = currentLanguage,
                        currentTypeFilter = currentTypeFilter,
                        availableLanguages = availableLanguages,
                        availableFilters = availableFilters,
                        currentSearch = currentSearch,
                        isEditing = isEditing,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = TranslateToolbarUiState(),
                )
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    private fun loadLanguages() {
        if (!this::viewModelScope.isInitialized) return

        viewModelScope.launch(dispatchers.io) {
            languageRepository.observeAll(projectId)
                .map { it.map { l -> completeLanguage(l) } }
                .onEach { projectLanguages ->
                    availableLanguages.value = projectLanguages
                    val baseLanguage = languageRepository.getBase(projectId)?.let { completeLanguage(it) }
                    if (baseLanguage != null && baseLanguage != currentLanguage.value) {
                        setLanguage(baseLanguage)
                    }
                }.launchIn(this)
        }
    }

    override fun setLanguage(value: LanguageModel) {
        if (currentLanguage.value == value) {
            return
        }

        currentLanguage.value = value
    }

    override fun setTypeFilter(value: TranslationUnitTypeFilter) {
        if (currentTypeFilter.value == value) {
            return
        }

        currentTypeFilter.value = value
    }

    override fun setSearch(value: String) {
        currentSearch.value = value
    }

    override fun onSearchFired() {
        viewModelScope.launch {
            _events.emit(TranslateToolbarComponent.Events.Search(currentSearch.value))
        }
    }

    override fun copyBase() {
        if (currentLanguage.value?.isBase == true) return

        viewModelScope.launch {
            _events.emit(TranslateToolbarComponent.Events.CopyBase)
        }
    }

    override fun setEditing(value: Boolean) {
        isEditing.value = value
    }

    override fun moveToPrevious() {
        viewModelScope.launch {
            _events.emit(TranslateToolbarComponent.Events.MoveToPrevious)
        }
    }

    override fun moveToNext() {
        viewModelScope.launch {
            _events.emit(TranslateToolbarComponent.Events.MoveToNext)
        }
    }

    override fun addUnit() {
        viewModelScope.launch {
            _events.emit(TranslateToolbarComponent.Events.AddUnit)
        }
    }

    override fun removeUnit() {
        viewModelScope.launch {
            _events.emit(TranslateToolbarComponent.Events.RemoveUnit)
        }
    }

    override fun validateUnits() {
        viewModelScope.launch {
            _events.emit(TranslateToolbarComponent.Events.ValidateUnits)
        }
    }
}
