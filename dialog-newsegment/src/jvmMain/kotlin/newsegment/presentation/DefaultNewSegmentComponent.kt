package newsegment.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import data.LanguageModel
import data.SegmentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import language.repo.LanguageRepository
import localized
import repository.repo.SegmentRepository
import kotlin.coroutines.CoroutineContext

internal class DefaultNewSegmentComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) : NewSegmentComponent, ComponentContext by componentContext {

    private val text = MutableStateFlow("")
    private val textError = MutableStateFlow("")
    private val key = MutableStateFlow("")
    private val keyError = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<NewSegmentUiState>
    override val done = MutableSharedFlow<SegmentModel?>()
    override lateinit var language: LanguageModel
    override var projectId = 0

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    key,
                    keyError,
                    text,
                    textError,
                    isLoading,
                ) { key, keyError, text, textError, isLoading ->
                    NewSegmentUiState(
                        key = key,
                        keyError = keyError,
                        text = text,
                        textError = textError,
                        isLoading = isLoading,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = NewSegmentUiState(),
                )
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun setKey(value: String) {
        key.value = value
    }

    override fun setText(value: String) {
        text.value = value
    }

    override fun close() {
        viewModelScope.launch(dispatchers.io) {
            done.emit(null)
        }
    }

    override fun submit() {
        keyError.value = ""
        textError.value = ""
        val key = key.value.trim()
        val text = text.value.trim()
        var valid = true
        viewModelScope.launch(dispatchers.io) {
            if (key.isEmpty()) {
                keyError.value = "message_missing_field".localized()
                valid = false
            } else {
                val existing = segmentRepository.getByKey(key = key, languageId = language.id)
                if (existing != null) {
                    keyError.value = "message_duplicate_key".localized()
                    valid = false
                }
            }
            if (text.isEmpty()) {
                textError.value = "message_missing_field".localized()
                valid = false
            }
            if (!valid) {
                return@launch
            }

            isLoading.value = true
            val res = SegmentModel(
                key = key,
                text = text,
            )
            val id = segmentRepository.create(model = res, languageId = language.id)

            // ensures segment is present in other languages
            val otherLanguaes = languageRepository.getAll(projectId).filter { it.code != language.code }
            for (lang in otherLanguaes) {
                val existing = segmentRepository.getByKey(key = key, languageId = lang.id)
                if (existing == null) {
                    segmentRepository.create(model = SegmentModel(key = key), languageId = lang.id)
                }
            }

            isLoading.value = false
            done.emit(res.copy(id = id))
        }
    }
}
