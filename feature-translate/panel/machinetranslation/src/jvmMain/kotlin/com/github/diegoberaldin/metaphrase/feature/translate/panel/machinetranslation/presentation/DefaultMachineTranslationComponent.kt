package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultMachineTranslationComponent(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val machineTranslationRepository: MachineTranslationRepository,
) : MachineTranslationComponent, ComponentContext by componentContext {

    private val isLoading = MutableStateFlow(false)
    private val translation = MutableStateFlow("")
    private val updateTextSwitch = MutableStateFlow(false)
    private lateinit var viewModelScope: CoroutineScope
    private var lastSourceLang = ""
    private var lastTargetLang = ""
    private var lastMessage = ""

    override lateinit var uiState: StateFlow<MachineTranslationUiState>
    override val copySourceEvents = MutableSharedFlow<String>()
    override val copyTargetEvents = MutableSharedFlow<Unit>()
    override val shareEvents = MutableSharedFlow<Boolean>()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    isLoading,
                    translation,
                    updateTextSwitch,
                ) { isLoading, translation, updateTextSwitch ->
                    MachineTranslationUiState(
                        isLoading = isLoading,
                        translation = translation,
                        updateTextSwitch = updateTextSwitch,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = MachineTranslationUiState(),
                )
            }
        }
    }

    override fun clear() {
        translation.value = ""
        lastSourceLang = ""
        lastTargetLang = ""
        lastMessage = ""
    }

    override fun load(key: String, projectId: Int, languageId: Int) {
        translation.value = ""
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
            isLoading.value = true
            val mtResult = machineTranslationRepository.getTranslation(
                message = lastMessage,
                sourceLang = lastSourceLang,
                targetLang = lastTargetLang,
            )
            translation.value = mtResult
            updateTextSwitch.getAndUpdate { !it }
            isLoading.value = false
        }
    }

    override fun setTranslation(value: String) {
        translation.value = value
    }

    override fun copyTarget() {
        viewModelScope.launch(dispatchers.io) {
            copyTargetEvents.emit(Unit)
        }
    }

    override fun copyTranslation(value: String) {
        translation.value = value
        updateTextSwitch.getAndUpdate { !it }
    }

    override fun insertTranslation() {
        viewModelScope.launch(dispatchers.io) {
            copySourceEvents.emit(translation.value)
        }
    }

    override fun share() {
        val sourceMessage = lastMessage.takeIf { it.isNotEmpty() } ?: return
        val sourceLang = lastSourceLang.takeIf { it.isNotEmpty() } ?: return
        val targetMessage = translation.value.takeIf { it.isNotEmpty() } ?: return
        val targetLang = lastTargetLang.takeIf { it.isNotEmpty() } ?: return
        viewModelScope.launch(dispatchers.io) {
            isLoading.value = true
            val success = runCatching {
                machineTranslationRepository.shareTranslation(
                    sourceMessage = sourceMessage,
                    sourceLang = sourceLang,
                    targetMessage = targetMessage,
                    targetLang = targetLang,
                )
            }.isSuccess
            isLoading.value = false
            shareEvents.emit(success)
        }
    }
}
