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
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
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
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultSettingsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<SettingsComponent.Intent, SettingsComponent.UiState, SettingsComponent.Effect> = DefaultMviModel(
        SettingsComponent.UiState(),
    ),
    private val completeLanguage: GetCompleteLanguageUseCase,
    private val keyStore: TemporaryKeyStore,
    private val machineTranslationRepository: MachineTranslationRepository,
) : SettingsComponent,
    MviModel<SettingsComponent.Intent, SettingsComponent.UiState, SettingsComponent.Effect> by mvi,
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
    private val dialogNavigation = SlotNavigation<SettingsComponent.DialogConfig>()

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
                    mvi.updateState { it.copy(isLoading = true) }
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
                    mvi.updateState {
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

    override fun reduce(intent: SettingsComponent.Intent) {
        when (intent) {
            is SettingsComponent.Intent.SetLanguage -> setLanguage(intent.value)
            is SettingsComponent.Intent.SetSimilarity -> setSimilarity(intent.value)
            is SettingsComponent.Intent.SetSpellcheckEnabled -> setSpellcheckEnabled(intent.value)
            is SettingsComponent.Intent.SetMachineTranslationProvider -> setMachineTranslationProvider(intent.index)
            is SettingsComponent.Intent.SetMachineTranslationKey -> setMachineTranslationKey(intent.value)
            SettingsComponent.Intent.OpenLoginDialog -> openLoginDialog()
            SettingsComponent.Intent.CloseDialog -> closeDialog()
            is SettingsComponent.Intent.GenerateMachineTranslationKey -> generateMachineTranslationKey(
                username = intent.username,
                password = intent.password,
            )
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

    private fun setMachineTranslationProvider(index: Int) {
        if (index !in MachineTranslationRepository.AVAILABLE_PROVIDERS.indices) return

        val provider = MachineTranslationRepository.AVAILABLE_PROVIDERS[index]
        mvi.updateState { it.copy(currentProvider = provider) }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.MachineTranslationProvider, index)
        }
    }

    private fun setMachineTranslationKey(value: String) {
        mvi.updateState { it.copy(key = value) }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.MachineTranslationKey, value)
        }
    }

    private fun openLoginDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(SettingsComponent.DialogConfig.Login)
        }
    }

    private fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(SettingsComponent.DialogConfig.None)
        }
    }

    private fun generateMachineTranslationKey(username: String, password: String) {
        val provider = uiState.value.currentProvider ?: return
        viewModelScope.launch(dispatchers.io) {
            mvi.updateState { it.copy(isLoading = true) }
            val key = machineTranslationRepository.generateKey(
                provider = provider,
                username = username,
                password = password,
            )
            setMachineTranslationKey(key)
            mvi.updateState { it.copy(isLoading = false) }
        }
    }
}
