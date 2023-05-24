package mainsettings.ui

import L10n
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import common.keystore.TemporaryKeyStore
import data.LanguageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import localized
import repository.usecase.GetCompleteLanguageUseCase
import kotlin.coroutines.CoroutineContext

class DefaultSettingsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val completeLanguage: GetCompleteLanguageUseCase,
    private val keyStore: TemporaryKeyStore,
) : SettingsComponent, ComponentContext by componentContext {

    private val availableLanguages = MutableStateFlow<List<LanguageModel>>(emptyList())
    private val currentLanguage = MutableStateFlow<LanguageModel?>(null)
    private val appVersion = MutableStateFlow("")
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<SettingsUiState>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    availableLanguages,
                    currentLanguage,
                    appVersion,
                ) { availableLanguages, currentLanguage, appVersion ->
                    SettingsUiState(
                        currentLanguage = currentLanguage,
                        availableLanguages = availableLanguages,
                        appVersion = appVersion,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = SettingsUiState(),
                )

                appVersion.value = System.getProperty("jpackage.app-version") ?: "[debug]"
                viewModelScope.launch(dispatchers.io) {
                    val langCode = "lang".localized()
                    currentLanguage.value = completeLanguage(LanguageModel(code = langCode))
                }

                availableLanguages.value = listOf(
                    "en",
                    "it",
                ).map {
                    completeLanguage(LanguageModel(code = it))
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun setLanguage(value: LanguageModel) {
        currentLanguage.value = value

        val langCode = value.code
        L10n.setLanguage(lang = langCode)
        viewModelScope.launch(dispatchers.io) {
            keyStore.save("lang", langCode)
        }
    }
}
