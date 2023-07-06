package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DefaultNewGlossaryTermComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<NewGlossaryTermComponent.ViewIntent, NewGlossaryTermComponent.UiState, NewGlossaryTermComponent.Effect> = DefaultMviModel(
        NewGlossaryTermComponent.UiState(),
    ),
) : NewGlossaryTermComponent,
    MviModel<NewGlossaryTermComponent.ViewIntent, NewGlossaryTermComponent.UiState, NewGlossaryTermComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

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

    override fun reduce(intent: NewGlossaryTermComponent.ViewIntent) {
        when (intent) {
            is NewGlossaryTermComponent.ViewIntent.SetSourceTerm -> setSourceTerm(intent.value)
            is NewGlossaryTermComponent.ViewIntent.SetTargetTerm -> setTargetTerm(intent.value)
            NewGlossaryTermComponent.ViewIntent.Submit -> submit()
        }
    }

    private fun setSourceTerm(value: String) {
        mvi.updateState { it.copy(sourceTerm = value) }
    }

    private fun setTargetTerm(value: String) {
        mvi.updateState { it.copy(targetTerm = value) }
    }

    private fun submit() {
        mvi.updateState {
            it.copy(
                sourceTermError = "",
                targetTermError = "",
            )
        }
        val source = uiState.value.sourceTerm.trim()
        val target = uiState.value.targetTerm.trim()
        var valid = true
        if (source.isEmpty()) {
            mvi.updateState { it.copy(sourceTermError = "message_missing_field".localized()) }
            valid = false
        }
        if (target.isEmpty()) {
            mvi.updateState { it.copy(targetTermError = "message_missing_field".localized()) }
            valid = false
        }
        if (!valid) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            val res = GlossaryTermPair(sourceLemma = source, targetLemma = target)
            mvi.emitEffect(NewGlossaryTermComponent.Effect.Done(res))
        }
    }
}
