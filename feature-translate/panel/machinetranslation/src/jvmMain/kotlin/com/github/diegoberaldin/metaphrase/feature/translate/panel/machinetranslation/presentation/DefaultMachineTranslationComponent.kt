package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultMachineTranslationComponent(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val machineTranslationRepository: MachineTranslationRepository,
    private val keyStore: TemporaryKeyStore,
) : MachineTranslationComponent, ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope
    private var lastSourceLang = ""
    private var lastTargetLang = ""
    private var lastMessage = ""

    override val uiState = MutableStateFlow(MachineTranslationUiState())
    override val copySourceEvents = MutableSharedFlow<String>()
    override val copyTargetEvents = MutableSharedFlow<Unit>()
    override val shareEvents = MutableSharedFlow<Boolean>()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun clear() {
        uiState.update {
            it.copy(
                translation = "",
                updateTextSwitch = !it.updateTextSwitch,
            )
        }
        lastSourceLang = ""
        lastTargetLang = ""
        lastMessage = ""
    }

    override fun load(key: String, projectId: Int, languageId: Int) {
        uiState.update {
            it.copy(
                translation = "",
                updateTextSwitch = !it.updateTextSwitch,
            )
        }
        lastSourceLang = ""
        lastTargetLang = ""
        lastMessage = ""
        viewModelScope.launch(dispatchers.io) {
            val sourceLanguage = languageRepository.getBase(projectId) ?: return@launch
            val targetLanguage = languageRepository.getById(languageId) ?: return@launch
            val message = segmentRepository.getByKey(key = key, languageId = sourceLanguage.id)?.text.orEmpty()
            lastSourceLang = sourceLanguage.code
            lastTargetLang = targetLanguage.code
            lastMessage = message
        }
    }

    override fun retrieve() {
        if (listOf(lastSourceLang, lastTargetLang, lastMessage).any { it.isEmpty() }) return

        viewModelScope.launch(dispatchers.io) {
            uiState.update { it.copy(isLoading = true) }
            val key = keyStore.get(KeyStoreKeys.MachineTranslationKey, "").takeIf { it.isNotEmpty() }
            val provider = keyStore.get(KeyStoreKeys.MachineTranslationProvider, 0).let {
                MachineTranslationRepository.AVAILABLE_PROVIDERS[it]
            }
            val mtResult = machineTranslationRepository.getTranslation(
                provider = provider,
                key = key,
                sourceMessage = lastMessage,
                sourceLang = lastSourceLang,
                targetLang = lastTargetLang,
            )
            uiState.update {
                it.copy(
                    translation = mtResult,
                    updateTextSwitch = !it.updateTextSwitch,
                    isLoading = false,
                )
            }
        }
    }

    override fun setTranslation(value: String) {
        uiState.update { it.copy(translation = value) }
    }

    override fun copyTarget() {
        viewModelScope.launch(dispatchers.io) {
            copyTargetEvents.emit(Unit)
        }
    }

    override fun copyTranslation(value: String) {
        uiState.update {
            it.copy(
                translation = value,
                updateTextSwitch = !it.updateTextSwitch,
            )
        }
    }

    override fun insertTranslation() {
        viewModelScope.launch(dispatchers.io) {
            val translation = uiState.value.translation
            copySourceEvents.emit(translation)
        }
    }

    override fun share() {
        val sourceMessage = lastMessage.takeIf { it.isNotEmpty() } ?: return
        val sourceLang = lastSourceLang.takeIf { it.isNotEmpty() } ?: return
        val targetMessage = uiState.value.translation.takeIf { it.isNotEmpty() } ?: return
        val targetLang = lastTargetLang.takeIf { it.isNotEmpty() } ?: return
        viewModelScope.launch(dispatchers.io) {
            uiState.update { it.copy(isLoading = true) }
            val key = keyStore.get(KeyStoreKeys.MachineTranslationKey, "").takeIf { it.isNotEmpty() }
            val provider = keyStore.get(KeyStoreKeys.MachineTranslationProvider, 0).let {
                MachineTranslationRepository.AVAILABLE_PROVIDERS[it]
            }
            val success = runCatching {
                machineTranslationRepository.shareTranslation(
                    provider = provider,
                    key = key,
                    sourceMessage = sourceMessage,
                    sourceLang = sourceLang,
                    targetMessage = targetMessage,
                    targetLang = targetLang,
                )
            }.isSuccess
            uiState.update { it.copy(isLoading = false) }
            shareEvents.emit(success)
        }
    }
}
