package dialogsettings.ui

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
import language.usecase.GetCompleteLanguageUseCase
import localized
import kotlin.coroutines.CoroutineContext

internal class DefaultSettingsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val completeLanguage: GetCompleteLanguageUseCase,
    private val keyStore: TemporaryKeyStore,
) : SettingsComponent, ComponentContext by componentContext {

    companion object {
        private val SUPPORTED_LANGUAGES = listOf(
            "en",
            "de",
            "fr",
            "es",
            "it",
            "pt",
        )
    }

    private val availableLanguages = MutableStateFlow<List<LanguageModel>>(emptyList())
    private val currentLanguage = MutableStateFlow<LanguageModel?>(null)
    private val similarityThreshold = MutableStateFlow("")
    private val spellcheckEnabled = MutableStateFlow(false)
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
                    similarityThreshold,
                    spellcheckEnabled,
                    appVersion,
                ) { availableLanguages, currentLanguage, similarityThreshold, spellcheckEnabled, appVersion ->
                    SettingsUiState(
                        currentLanguage = currentLanguage,
                        availableLanguages = availableLanguages,
                        similarityThreshold = similarityThreshold,
                        spellcheckEnabled = spellcheckEnabled,
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
                    val similarity = keyStore.get("similarity_threshold", 75)
                    similarityThreshold.value = similarity.toString()
                    val isSpellcheckEnabled = keyStore.get("spellcheck_enabled", false)
                    spellcheckEnabled.value = isSpellcheckEnabled
                }

                availableLanguages.value = SUPPORTED_LANGUAGES.map {
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

    override fun setSimilarity(value: String) {
        val newValue = (value.toIntOrNull() ?: 75).coerceIn(0, 100)
        similarityThreshold.value = newValue.toString()
        viewModelScope.launch(dispatchers.io) {
            keyStore.save("similarity_threshold", newValue)
        }
    }

    override fun setSpellcheckEnabled(value: Boolean) {
        spellcheckEnabled.value = value
        viewModelScope.launch(dispatchers.io) {
            keyStore.save("spellcheck_enabled", value)
        }
    }
}
