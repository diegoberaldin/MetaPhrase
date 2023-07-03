package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DefaultNewGlossaryTermComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
) : NewGlossaryTermComponent, ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

    override val uiState = MutableStateFlow(NewGlossaryTermUiState())
    override val done = MutableSharedFlow<GlossaryTermPair>()

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

    override fun setSourceTerm(value: String) {
        uiState.update { it.copy(sourceTerm = value) }
    }

    override fun setTargetTerm(value: String) {
        uiState.update { it.copy(targetTerm = value) }
    }

    override fun submit() {
        uiState.update {
            it.copy(
                sourceTermError = "",
                targetTermError = "",
            )
        }
        val source = uiState.value.sourceTerm.trim()
        val target = uiState.value.targetTerm.trim()
        var valid = true
        if (source.isEmpty()) {
            uiState.update { it.copy(sourceTermError = "message_missing_field".localized()) }
            valid = false
        }
        if (target.isEmpty()) {
            uiState.update { it.copy(targetTermError = "message_missing_field".localized()) }
            valid = false
        }
        if (!valid) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            done.emit(GlossaryTermPair(sourceLemma = source, targetLemma = target))
        }
    }
}
