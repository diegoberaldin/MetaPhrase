package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.combine
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultBrowseMemoryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val memoryEntryRepository: MemoryEntryRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : BrowseMemoryComponent, ComponentContext by componentContext {

    private val sourceLanguage = MutableStateFlow<LanguageModel?>(null)
    private val targetLanguage = MutableStateFlow<LanguageModel?>(null)
    private val availableSourceLanguages = MutableStateFlow<List<LanguageModel>>(emptyList())
    private val availableTargetLanguages = MutableStateFlow<List<LanguageModel>>(emptyList())
    private val search = MutableStateFlow("")
    private val entries = MutableStateFlow<List<TranslationMemoryEntryModel>>(emptyList())
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<BrowseMemoryUiState>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    sourceLanguage,
                    availableSourceLanguages,
                    targetLanguage,
                    availableTargetLanguages,
                    search,
                    entries,
                ) { sourceLanguage, availableSourceLanguages, targetLanguage, availableTargetLanguages, search, entries ->
                    BrowseMemoryUiState(
                        sourceLanguage,
                        availableSourceLanguages,
                        targetLanguage,
                        availableTargetLanguages,
                        search,
                        entries,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = BrowseMemoryUiState(),
                )

                refreshLanguages()
                load()
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun setLanguages(source: LanguageModel?, target: LanguageModel?) {
        sourceLanguage.value = source?.let { completeLanguage(it) }
        targetLanguage.value = target
        refreshLanguages()
        load()
    }

    private fun refreshLanguages() {
        if (!::viewModelScope.isInitialized) return
        viewModelScope.launch(dispatchers.io) {
            val tmLanguages = memoryEntryRepository.getLanguageCodes().map {
                completeLanguage(LanguageModel(code = it))
            }
            if (targetLanguage.value?.code == sourceLanguage.value?.code) {
                targetLanguage.value = tmLanguages.firstOrNull { it.code != sourceLanguage.value?.code }
            }
            val sourceLang = sourceLanguage.value
            val targetLang = targetLanguage.value

            availableSourceLanguages.value = tmLanguages.filter { it.code != targetLang?.code }
            availableTargetLanguages.value = tmLanguages.filter { it.code != sourceLang?.code }
        }
    }

    private fun load() {
        if (!::viewModelScope.isInitialized) return
        val sourceLangCode = sourceLanguage.value?.code?.takeIf { it.isNotEmpty() } ?: return
        val targetLangCode = targetLanguage.value?.code?.takeIf { it.isNotEmpty() } ?: return
        val currentSearch = search.value

        viewModelScope.launch(dispatchers.io) {
            entries.value = memoryEntryRepository.getSources(
                sourceLang = sourceLangCode,
                targetLang = targetLangCode,
                search = currentSearch,
            )
        }
    }

    override fun setSourceLanguage(value: LanguageModel?) {
        sourceLanguage.value = value
        refreshLanguages()
        load()
    }

    override fun setTargetLanguage(value: LanguageModel?) {
        targetLanguage.value = value
        refreshLanguages()
        load()
    }

    override fun setSearch(value: String) {
        search.value = value
    }

    override fun onSearchFired() {
        load()
    }

    override fun deleteEntry(index: Int) {
        val entry = entries.value[index]
        viewModelScope.launch(dispatchers.io) {
            memoryEntryRepository.delete(entry)
            load()
        }
    }
}
