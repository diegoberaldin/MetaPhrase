package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultMachineTranslationComponent(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<MachineTranslationComponent.Intent, MachineTranslationComponent.UiState, MachineTranslationComponent.Effect> = DefaultMviModel(
        MachineTranslationComponent.UiState(),
    ),
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val machineTranslationRepository: MachineTranslationRepository,
    private val keyStore: TemporaryKeyStore,
) : MachineTranslationComponent,
    MviModel<MachineTranslationComponent.Intent, MachineTranslationComponent.UiState, MachineTranslationComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope
    private var lastSourceLang = ""
    private var lastTargetLang = ""
    private var lastMessage = ""

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                machineTranslationRepository.supportsContributions.onEach { supported ->
                    mvi.updateState {
                        it.copy(
                            supportsContributions = supported,
                        )
                    }
                }.launchIn(viewModelScope)
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: MachineTranslationComponent.Intent) {
        when (intent) {
            MachineTranslationComponent.Intent.Clear -> clear()
            MachineTranslationComponent.Intent.CopyTarget -> copyTarget()
            is MachineTranslationComponent.Intent.CopyTranslation -> copyTranslation(intent.value)
            MachineTranslationComponent.Intent.InsertTranslation -> insertTranslation()
            is MachineTranslationComponent.Intent.Load -> load(
                key = intent.key,
                projectId = intent.projectId,
                languageId = intent.languageId,
            )

            MachineTranslationComponent.Intent.Retrieve -> retrieve()
            is MachineTranslationComponent.Intent.SetTranslation -> setTranslation(intent.value)
            MachineTranslationComponent.Intent.Share -> share()
        }
    }

    private fun clear() {
        mvi.updateState {
            it.copy(
                translation = "",
                updateTextSwitch = !it.updateTextSwitch,
            )
        }
        lastSourceLang = ""
        lastTargetLang = ""
        lastMessage = ""
    }

    private fun load(key: String, projectId: Int, languageId: Int) {
        mvi.updateState {
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

    private fun retrieve() {
        if (listOf(lastSourceLang, lastTargetLang, lastMessage).any { it.isEmpty() }) return

        viewModelScope.launch(dispatchers.io) {
            mvi.updateState { it.copy(isLoading = true) }
            val key = keyStore.get(KeyStoreKeys.MachineTranslationKey, "").takeIf { it.isNotEmpty() }
            val mtResult = machineTranslationRepository.getTranslation(
                key = key,
                sourceMessage = lastMessage,
                sourceLang = lastSourceLang,
                targetLang = lastTargetLang,
            )
            mvi.updateState {
                it.copy(
                    translation = mtResult,
                    updateTextSwitch = !it.updateTextSwitch,
                    isLoading = false,
                )
            }
        }
    }

    private fun setTranslation(value: String) {
        mvi.updateState { it.copy(translation = value) }
    }

    private fun copyTarget() {
        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(MachineTranslationComponent.Effect.CopyTarget)
        }
    }

    private fun copyTranslation(value: String) {
        mvi.updateState {
            it.copy(
                translation = value,
                updateTextSwitch = !it.updateTextSwitch,
            )
        }
    }

    private fun insertTranslation() {
        viewModelScope.launch(dispatchers.io) {
            val translation = uiState.value.translation
            mvi.emitEffect(MachineTranslationComponent.Effect.CopySource(translation))
        }
    }

    private fun share() {
        val sourceMessage = lastMessage.takeIf { it.isNotEmpty() } ?: return
        val sourceLang = lastSourceLang.takeIf { it.isNotEmpty() } ?: return
        val targetMessage = uiState.value.translation.takeIf { it.isNotEmpty() } ?: return
        val targetLang = lastTargetLang.takeIf { it.isNotEmpty() } ?: return
        viewModelScope.launch(dispatchers.io) {
            mvi.updateState { it.copy(isLoading = true) }
            val key = keyStore.get(KeyStoreKeys.MachineTranslationKey, "").takeIf { it.isNotEmpty() }
            val success = runCatching {
                machineTranslationRepository.shareTranslation(
                    key = key,
                    sourceMessage = sourceMessage,
                    sourceLang = sourceLang,
                    targetMessage = targetMessage,
                    targetLang = targetLang,
                )
            }.isSuccess
            mvi.updateState { it.copy(isLoading = false) }
            mvi.emitEffect(MachineTranslationComponent.Effect.Share(success))
        }
    }
}
