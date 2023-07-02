package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.feature.main.settings.dialog.login.presentation.LoginComponent
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultSettingsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val completeLanguage: GetCompleteLanguageUseCase,
    private val keyStore: TemporaryKeyStore,
    private val machineTranslationRepository: MachineTranslationRepository,
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

    private lateinit var viewModelScope: CoroutineScope
    private val dialogNavigation = SlotNavigation<SettingsComponent.DialogConfig>()

    override val uiState = MutableStateFlow(SettingsUiState())
    override val dialog: Value<ChildSlot<SettingsComponent.DialogConfig, *>> = childSlot(
        source = dialogNavigation,
        key = "SettingsComponentDialogSlot",
        childFactory = { config, context ->
            when (config) {
                SettingsComponent.DialogConfig.Login -> getByInjection<LoginComponent>(context, coroutineContext)

                else -> Unit
            }
        },
    )

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                viewModelScope.launch(dispatchers.io) {
                    uiState.update { it.copy(isLoading = true) }
                    val version = System.getProperty("jpackage.app-version") ?: "[debug]"
                    val langCode = "lang".localized()
                    val currentLanguage = completeLanguage(LanguageModel(code = langCode))
                    val similarity = keyStore.get(KeyStoreKeys.SimilarityThreshold, 75)
                    val isSpellcheckEnabled = keyStore.get(KeyStoreKeys.SpellcheckEnabled, false)
                    val providerIndex = keyStore.get(KeyStoreKeys.MachineTranslationProvider, 0)
                    val providerKey = keyStore.get(KeyStoreKeys.MachineTranslationKey, "")
                    val languages = SUPPORTED_LANGUAGES.map { l ->
                        completeLanguage(LanguageModel(code = l))
                    }
                    val currentProvider = MachineTranslationRepository.AVAILABLE_PROVIDERS[providerIndex]
                    uiState.update {
                        it.copy(
                            currentLanguage = currentLanguage,
                            similarityThreshold = similarity.toString(),
                            spellcheckEnabled = isSpellcheckEnabled,
                            availableProviders = MachineTranslationRepository.AVAILABLE_PROVIDERS,
                            currentProvider = currentProvider,
                            key = providerKey,
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

    override fun setLanguage(value: LanguageModel) {
        uiState.update { it.copy(currentLanguage = value) }

        val langCode = value.code
        L10n.setLanguage(lang = langCode)
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.AppLanguage, langCode)
        }
    }

    override fun setSimilarity(value: String) {
        val newValue = (value.toIntOrNull() ?: 75).coerceIn(0, 100)
        uiState.update { it.copy(similarityThreshold = newValue.toString()) }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.SimilarityThreshold, newValue)
        }
    }

    override fun setSpellcheckEnabled(value: Boolean) {
        uiState.update { it.copy(spellcheckEnabled = value) }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.SpellcheckEnabled, value)
        }
    }

    override fun setMachineTranslationProvider(index: Int) {
        if (index !in MachineTranslationRepository.AVAILABLE_PROVIDERS.indices) return

        val provider = MachineTranslationRepository.AVAILABLE_PROVIDERS[index]
        uiState.update { it.copy(currentProvider = provider) }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.MachineTranslationProvider, index)
        }
    }

    override fun setMachineTranslationKey(value: String) {
        uiState.update { it.copy(key = value) }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.MachineTranslationKey, value)
        }
    }

    override fun openLoginDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(SettingsComponent.DialogConfig.Login)
        }
    }

    override fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(SettingsComponent.DialogConfig.None)
        }
    }

    override fun generateMachineTranslationKey(username: String, password: String) {
        val provider = uiState.value.currentProvider ?: return
        viewModelScope.launch(dispatchers.io) {
            uiState.update { it.copy(isLoading = true) }
            val key = machineTranslationRepository.generateKey(
                provider = provider,
                username = username,
                password = password,
            )
            setMachineTranslationKey(key)
            uiState.update { it.copy(isLoading = false) }
        }
    }
}
