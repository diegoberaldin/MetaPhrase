package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private lateinit var viewModelScope: CoroutineScope
    private var _events = MutableSharedFlow<TranslateToolbarComponent.Events>()

    override val uiState = MutableStateFlow(TranslateToolbarUiState())
    override lateinit var currentLanguage: StateFlow<LanguageModel?>
    override val events: SharedFlow<TranslateToolbarComponent.Events> = _events.asSharedFlow()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState.update {
                    it.copy(
                        availableFilters = listOf(
                            TranslationUnitTypeFilter.ALL,
                            TranslationUnitTypeFilter.TRANSLATABLE,
                            TranslationUnitTypeFilter.UNTRANSLATED,
                        ),
                    )
                }
                currentLanguage = uiState.map { it.currentLanguage }.stateIn(
                    scope = viewModelScope,
                    initialValue = null,
                    started = SharingStarted.WhileSubscribed(5_000),
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
                    uiState.update { it.copy(availableLanguages = projectLanguages) }
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

        uiState.update { it.copy(currentLanguage = value) }
    }

    override fun setTypeFilter(value: TranslationUnitTypeFilter) {
        if (uiState.value.currentTypeFilter == value) {
            return
        }

        uiState.update { it.copy(currentTypeFilter = value) }
    }

    override fun setSearch(value: String) {
        uiState.update { it.copy(currentSearch = value) }
    }

    override fun onSearchFired() {
        viewModelScope.launch(dispatchers.io) {
            _events.emit(TranslateToolbarComponent.Events.Search(uiState.value.currentSearch))
        }
    }

    override fun copyBase() {
        if (uiState.value.currentLanguage?.isBase != false) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _events.emit(TranslateToolbarComponent.Events.CopyBase)
        }
    }

    override fun setEditing(value: Boolean) {
        uiState.update { it.copy(isEditing = value) }
    }

    override fun moveToPrevious() {
        viewModelScope.launch(dispatchers.io) {
            _events.emit(TranslateToolbarComponent.Events.MoveToPrevious)
        }
    }

    override fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            _events.emit(TranslateToolbarComponent.Events.MoveToNext)
        }
    }

    override fun addUnit() {
        viewModelScope.launch(dispatchers.io) {
            _events.emit(TranslateToolbarComponent.Events.AddUnit)
        }
    }

    override fun removeUnit() {
        viewModelScope.launch(dispatchers.io) {
            _events.emit(TranslateToolbarComponent.Events.RemoveUnit)
        }
    }

    override fun validateUnits() {
        if (uiState.value.currentLanguage?.isBase != false) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _events.emit(TranslateToolbarComponent.Events.ValidateUnits)
        }
    }
}
