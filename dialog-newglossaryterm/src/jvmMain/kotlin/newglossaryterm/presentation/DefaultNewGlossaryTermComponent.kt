package newglossaryterm.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
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
import localized
import kotlin.coroutines.CoroutineContext

class DefaultNewGlossaryTermComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
) : NewGlossaryTermComponent, ComponentContext by componentContext {

    private val sourceTerm = MutableStateFlow("")
    private val sourceTermError = MutableStateFlow("")
    private val targetTerm = MutableStateFlow("")
    private val targetTermError = MutableStateFlow("")
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<NewGlossaryTermUiState>
    override val done = MutableSharedFlow<Pair<String?, String?>>()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    sourceTerm,
                    sourceTermError,
                    targetTerm,
                    targetTermError,
                ) { sourceTerm, sourceTermError, targetTerm, targetTermError ->
                    NewGlossaryTermUiState(
                        sourceTerm = sourceTerm,
                        sourceTermError = sourceTermError,
                        targetTerm = targetTerm,
                        targetTermError = targetTermError,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = NewGlossaryTermUiState(),
                )
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun setSourceTerm(value: String) {
        sourceTerm.value = value
    }

    override fun setTargetTerm(value: String) {
        targetTerm.value = value
    }

    override fun submit() {
        sourceTermError.value = ""
        targetTermError.value = ""
        val source = sourceTerm.value.trim()
        val target = targetTerm.value.trim()
        var valid = true
        if (source.isEmpty()) {
            sourceTermError.value = "message_missing_field".localized()
            valid = false
        }
        if (target.isEmpty()) {
            sourceTermError.value = "message_missing_field".localized()
            valid = false
        }
        if (!valid) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            done.emit(source to target)
        }
    }
}
