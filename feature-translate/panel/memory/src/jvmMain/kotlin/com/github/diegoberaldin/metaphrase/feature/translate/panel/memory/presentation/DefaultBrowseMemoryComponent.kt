package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultBrowseMemoryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val memoryEntryRepository: MemoryEntryRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : BrowseMemoryComponent, ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

    override val uiState = MutableStateFlow(BrowseMemoryUiState())

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

    override fun setLanguages(source: LanguageModel?, target: LanguageModel?) {
        uiState.update {
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

            uiState.update {
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
        uiState.update { it.copy(entries = entries) }
    }

    override fun setSourceLanguage(value: LanguageModel?) {
        uiState.update { it.copy(sourceLanguage = value) }
        refreshLanguages()
    }

    override fun setTargetLanguage(value: LanguageModel?) {
        uiState.update { it.copy(targetLanguage = value) }
        refreshLanguages()
    }

    override fun setSearch(value: String) {
        uiState.update { it.copy(currentSearch = value) }
    }

    override fun onSearchFired() {
        viewModelScope.launch(dispatchers.io) {
            load()
        }
    }

    override fun deleteEntry(index: Int) {
        val entry = uiState.value.entries[index]
        viewModelScope.launch(dispatchers.io) {
            memoryEntryRepository.delete(entry)
            load()
        }
    }
}
