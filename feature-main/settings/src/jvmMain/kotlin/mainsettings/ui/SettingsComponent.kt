package mainsettings.ui

import com.arkivanov.decompose.ComponentContext
import common.utils.getByInjection
import data.LanguageModel
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface SettingsComponent {

    val uiState: StateFlow<SettingsUiState>

    fun setLanguage(value: LanguageModel)

    companion object {
        fun newInstance(
            componentContext: ComponentContext,
            coroutineContext: CoroutineContext,
        ) = DefaultSettingsComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            dispatchers = getByInjection(),
            completeLanguage = getByInjection(),
            keyStore = getByInjection(),
        )
    }
}
