package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultTranslateToolbarComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<TranslateToolbarComponent.ViewIntent, TranslateToolbarComponent.UiState, TranslateToolbarComponent.Effect> = DefaultMviModel(
        TranslateToolbarComponent.UiState(),
    ),
    private val languageRepository: LanguageRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : TranslateToolbarComponent,
    MviModel<TranslateToolbarComponent.ViewIntent, TranslateToolbarComponent.UiState, TranslateToolbarComponent.Effect> by mvi,
    ComponentContext by componentContext {

    override var projectId: Int = 0
        set(value) {
            field = value
            loadLanguages()
        }
    private lateinit var viewModelScope: CoroutineScope

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                mvi.updateState {
                    it.copy(
                        availableFilters = listOf(
                            TranslationUnitTypeFilter.ALL,
                            TranslationUnitTypeFilter.TRANSLATABLE,
                            TranslationUnitTypeFilter.UNTRANSLATED,
                        ),
                    )
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: TranslateToolbarComponent.ViewIntent) {
        when (intent) {
            TranslateToolbarComponent.ViewIntent.AddUnit -> addUnit()
            TranslateToolbarComponent.ViewIntent.CopyBase -> copyBase()
            TranslateToolbarComponent.ViewIntent.MoveToNext -> moveToNext()
            TranslateToolbarComponent.ViewIntent.MoveToPrevious -> moveToPrevious()
            TranslateToolbarComponent.ViewIntent.OnSearchFired -> onSearchFired()
            TranslateToolbarComponent.ViewIntent.RemoveUnit -> removeUnit()
            is TranslateToolbarComponent.ViewIntent.SetEditing -> setEditing(intent.value)
            is TranslateToolbarComponent.ViewIntent.SetLanguage -> setLanguage(intent.value)
            is TranslateToolbarComponent.ViewIntent.SetSearch -> setSearch(intent.value)
            is TranslateToolbarComponent.ViewIntent.SetTypeFilter -> setTypeFilter(intent.value)
            TranslateToolbarComponent.ViewIntent.ValidateUnits -> validateUnits()
        }
    }

    private fun loadLanguages() {
        if (!this::viewModelScope.isInitialized) return

        viewModelScope.launch(dispatchers.io) {
            languageRepository.observeAll(projectId)
                .map { it.map { l -> completeLanguage(l) } }
                .onEach { projectLanguages ->
                    mvi.updateState { it.copy(availableLanguages = projectLanguages) }
                    val baseLanguage = languageRepository.getBase(projectId)?.let { completeLanguage(it) }
                    if (baseLanguage != null && baseLanguage != uiState.value.currentLanguage) {
                        setLanguage(baseLanguage)
                    }
                }.launchIn(this)
        }
    }

    private fun setLanguage(value: LanguageModel) {
        if (uiState.value.currentLanguage == value) {
            return
        }

        mvi.updateState { it.copy(currentLanguage = value) }
    }

    private fun setTypeFilter(value: TranslationUnitTypeFilter) {
        if (uiState.value.currentTypeFilter == value) {
            return
        }

        mvi.updateState { it.copy(currentTypeFilter = value) }
    }

    private fun setSearch(value: String) {
        mvi.updateState { it.copy(currentSearch = value) }
    }

    private fun onSearchFired() {
        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(TranslateToolbarComponent.Effect.Search(uiState.value.currentSearch))
        }
    }

    private fun copyBase() {
        if (uiState.value.currentLanguage?.isBase != false) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(TranslateToolbarComponent.Effect.CopyBase)
        }
    }

    private fun setEditing(value: Boolean) {
        mvi.updateState { it.copy(isEditing = value) }
    }

    private fun moveToPrevious() {
        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(TranslateToolbarComponent.Effect.MoveToPrevious)
        }
    }

    private fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(TranslateToolbarComponent.Effect.MoveToNext)
        }
    }

    private fun addUnit() {
        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(TranslateToolbarComponent.Effect.AddUnit)
        }
    }

    private fun removeUnit() {
        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(TranslateToolbarComponent.Effect.RemoveUnit)
        }
    }

    private fun validateUnits() {
        if (uiState.value.currentLanguage?.isBase != false) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(TranslateToolbarComponent.Effect.ValidateUnits)
        }
    }
}
