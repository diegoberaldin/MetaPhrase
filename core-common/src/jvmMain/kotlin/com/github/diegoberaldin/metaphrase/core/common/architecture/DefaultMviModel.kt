package com.github.diegoberaldin.metaphrase.core.common.architecture

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Basic implementation of the MVI model. This is useful to easily implement the interface by delegation,
 * minimizing the amount of code that is needed when integrating the MVI pattern.
 * The [updateState] and [emitEffect] methods are shortcuts to easily update the UI state and emit a side effect.
 *
 * @param Intent class of intents
 * @param State class of UI state
 * @param Effect class of effects
 * @constructor Create [DefaultMviModel]
 *
 * @param initialState initial UI state
 */
class DefaultMviModel<Intent, State, Effect>(
    initialState: State,
) : MviModel<Intent, State, Effect> {

    override val uiState = MutableStateFlow(initialState)
    override val effects = MutableSharedFlow<Effect>()

    /**
     * Emit an effect (event).
     *
     * @param value Value
     */
    suspend fun emitEffect(value: Effect) {
        effects.emit(value)
    }

    /**
     * Update the UI state.
     *
     * @param block Block
     */
    inline fun updateState(block: (State) -> State) {
        uiState.update { block(uiState.value) }
    }

    override fun reduce(intent: Intent) {
        // Noop
    }
}
