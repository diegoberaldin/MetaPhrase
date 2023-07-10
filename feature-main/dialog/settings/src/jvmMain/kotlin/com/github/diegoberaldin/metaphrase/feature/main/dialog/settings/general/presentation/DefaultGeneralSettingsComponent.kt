package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DefaultGeneralSettingsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<GeneralSettingsComponent.Intent, GeneralSettingsComponent.UiState, GeneralSettingsComponent.Effect> = DefaultMviModel(
        GeneralSettingsComponent.UiState(),
    ),
    private val completeLanguage: GetCompleteLanguageUseCase,
    private val keyStore: TemporaryKeyStore,
) : GeneralSettingsComponent,
    MviModel<GeneralSettingsComponent.Intent, GeneralSettingsComponent.UiState, GeneralSettingsComponent.Effect> by mvi,
    ComponentContext by componentContext {

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

    private lateinit var viewModelScope: CoroutineScope

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                viewModelScope.launch(dispatchers.io) {
                    mvi.updateState { it.copy(isLoading = true) }
                    val version = System.getProperty("jpackage.app-version") ?: "[debug]"
                    val langCode = "lang".localized()
                    val currentLanguage = completeLanguage(LanguageModel(code = langCode))
                    val similarity = keyStore.get(KeyStoreKeys.SimilarityThreshold, 75)
                    val isSpellcheckEnabled = keyStore.get(KeyStoreKeys.SpellcheckEnabled, false)
                    val languages = SUPPORTED_LANGUAGES.map { l ->
                        completeLanguage(LanguageModel(code = l))
                    }
                    mvi.updateState {
                        it.copy(
                            currentLanguage = currentLanguage,
                            similarityThreshold = similarity.toString(),
                            spellcheckEnabled = isSpellcheckEnabled,
                            availableLanguages = languages,
                            appVersion = version,
                            isLoading = false,
                        )
                    }
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: GeneralSettingsComponent.Intent) {
        when (intent) {
            is GeneralSettingsComponent.Intent.SetLanguage -> setLanguage(intent.value)
            is GeneralSettingsComponent.Intent.SetSimilarity -> setSimilarity(intent.value)
            is GeneralSettingsComponent.Intent.SetSpellcheckEnabled -> setSpellcheckEnabled(intent.value)
        }
    }

    private fun setLanguage(value: LanguageModel) {
        mvi.updateState { it.copy(currentLanguage = value) }

        val langCode = value.code
        L10n.setLanguage(lang = langCode)
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.AppLanguage, langCode)
        }
    }

    private fun setSimilarity(value: String) {
        val newValue = (value.toIntOrNull() ?: 75).coerceIn(0, 100)
        mvi.updateState { it.copy(similarityThreshold = newValue.toString()) }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.SimilarityThreshold, newValue)
        }
    }

    private fun setSpellcheckEnabled(value: Boolean) {
        mvi.updateState { it.copy(spellcheckEnabled = value) }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.SpellcheckEnabled, value)
        }
    }
}
