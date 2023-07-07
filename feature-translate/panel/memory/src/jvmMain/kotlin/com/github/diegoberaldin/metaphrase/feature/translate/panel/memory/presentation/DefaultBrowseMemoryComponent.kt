package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultBrowseMemoryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<BrowseMemoryComponent.ViewIntent, BrowseMemoryComponent.UiState, BrowseMemoryComponent.Effect> = DefaultMviModel(
        BrowseMemoryComponent.UiState(),
    ),
    private val memoryEntryRepository: MemoryEntryRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : BrowseMemoryComponent,
    MviModel<BrowseMemoryComponent.ViewIntent, BrowseMemoryComponent.UiState, BrowseMemoryComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                refreshLanguages()
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: BrowseMemoryComponent.ViewIntent) {
        when (intent) {
            is BrowseMemoryComponent.ViewIntent.DeleteEntry -> deleteEntry(intent.index)
            BrowseMemoryComponent.ViewIntent.OnSearchFired -> onSearchFired()
            is BrowseMemoryComponent.ViewIntent.SetLanguages -> setLanguages(
                source = intent.source,
                target = intent.target,
            )

            is BrowseMemoryComponent.ViewIntent.SetSearch -> setSearch(intent.value)
            is BrowseMemoryComponent.ViewIntent.SetSourceLanguage -> setSourceLanguage(intent.value)
            is BrowseMemoryComponent.ViewIntent.SetTargetLanguage -> setTargetLanguage(intent.value)
        }
    }

    private fun setLanguages(source: LanguageModel?, target: LanguageModel?) {
        mvi.updateState {
            it.copy(
                sourceLanguage = source?.let { lang -> completeLanguage(lang) },
                targetLanguage = target,
            )
        }
        refreshLanguages()
    }

    private fun refreshLanguages() {
        if (!::viewModelScope.isInitialized) return

        viewModelScope.launch(dispatchers.io) {
            val tmLanguages = memoryEntryRepository.getLanguageCodes().map {
                completeLanguage(LanguageModel(code = it))
            }
            var targetLanguage = uiState.value.targetLanguage
            val sourceLanguage = uiState.value.sourceLanguage
            if (targetLanguage?.code == sourceLanguage?.code) {
                targetLanguage = tmLanguages.firstOrNull { it.code != sourceLanguage?.code }
            }

            mvi.updateState {
                it.copy(
                    availableSourceLanguages = tmLanguages.filter { l -> l.code != targetLanguage?.code },
                    availableTargetLanguages = tmLanguages.filter { l -> l.code != sourceLanguage?.code },
                )
            }

            load()
        }
    }

    private suspend fun load() {
        val sourceLangCode = uiState.value.sourceLanguage?.code?.takeIf { it.isNotEmpty() } ?: return
        val targetLangCode = uiState.value.targetLanguage?.code?.takeIf { it.isNotEmpty() } ?: return
        val currentSearch = uiState.value.currentSearch

        val entries = memoryEntryRepository.getEntries(
            sourceLang = sourceLangCode,
            targetLang = targetLangCode,
            search = currentSearch,
        )
        mvi.updateState { it.copy(entries = entries) }
    }

    private fun setSourceLanguage(value: LanguageModel?) {
        mvi.updateState { it.copy(sourceLanguage = value) }
        refreshLanguages()
    }

    private fun setTargetLanguage(value: LanguageModel?) {
        mvi.updateState { it.copy(targetLanguage = value) }
        refreshLanguages()
    }

    private fun setSearch(value: String) {
        mvi.updateState { it.copy(currentSearch = value) }
    }

    private fun onSearchFired() {
        viewModelScope.launch(dispatchers.io) {
            load()
        }
    }

    private fun deleteEntry(index: Int) {
        val entry = uiState.value.entries[index]
        viewModelScope.launch(dispatchers.io) {
            memoryEntryRepository.delete(entry)
            load()
        }
    }
}
