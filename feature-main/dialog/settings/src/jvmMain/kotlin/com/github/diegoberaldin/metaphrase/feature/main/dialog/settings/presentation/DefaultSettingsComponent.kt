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
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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

    private val availableLanguages = MutableStateFlow<List<LanguageModel>>(emptyList())
    private val currentLanguage = MutableStateFlow<LanguageModel?>(null)
    private val similarityThreshold = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private val spellcheckEnabled = MutableStateFlow(false)
    private val machineTranslationProviders = MutableStateFlow(MachineTranslationRepository.AVAILABLE_PROVIDERS)
    private val currentMachineTranslationProvider = MutableStateFlow<MachineTranslationProvider?>(null)
    private val machineTranslationKey = MutableStateFlow("")
    private val appVersion = MutableStateFlow("")
    private lateinit var viewModelScope: CoroutineScope
    private val dialogNavigation = SlotNavigation<SettingsComponent.DialogConfig>()

    override lateinit var uiState: StateFlow<SettingsUiState>
    override lateinit var languageUiState: StateFlow<SettingsLanguageUiState>
    override lateinit var machineTranslationUiState: StateFlow<SettingsMachineTranslationUiState>
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
                uiState = combine(
                    isLoading,
                    similarityThreshold,
                    spellcheckEnabled,
                    appVersion,
                ) { isLoading, similarityThreshold, spellcheckEnabled, appVersion ->
                    SettingsUiState(
                        isLoading = isLoading,
                        similarityThreshold = similarityThreshold,
                        spellcheckEnabled = spellcheckEnabled,
                        appVersion = appVersion,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = SettingsUiState(),
                )
                languageUiState = combine(
                    availableLanguages,
                    currentLanguage,
                ) { availableLanguages, currentLanguage ->
                    SettingsLanguageUiState(
                        currentLanguage = currentLanguage,
                        availableLanguages = availableLanguages,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = SettingsLanguageUiState(),
                )
                machineTranslationUiState = combine(
                    machineTranslationProviders,
                    currentMachineTranslationProvider,
                    machineTranslationKey,
                ) { availableProviders, currentProvider, key ->
                    SettingsMachineTranslationUiState(
                        availableProviders = availableProviders,
                        currentProvider = currentProvider,
                        key = key,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = SettingsMachineTranslationUiState(),
                )

                appVersion.value = System.getProperty("jpackage.app-version") ?: "[debug]"
                viewModelScope.launch(dispatchers.io) {
                    isLoading.value = true
                    val langCode = "lang".localized()
                    currentLanguage.value = completeLanguage(LanguageModel(code = langCode))
                    val similarity = keyStore.get(KeyStoreKeys.SimilarityThreshold, 75)
                    similarityThreshold.value = similarity.toString()
                    val isSpellcheckEnabled = keyStore.get(KeyStoreKeys.SpellcheckEnabled, false)
                    spellcheckEnabled.value = isSpellcheckEnabled
                    val providerIndex = keyStore.get(KeyStoreKeys.MachineTranslationProvider, 0)
                    currentMachineTranslationProvider.value =
                        MachineTranslationRepository.AVAILABLE_PROVIDERS[providerIndex]
                    val providerKey = keyStore.get(KeyStoreKeys.MachineTranslationKey, "")
                    machineTranslationKey.value = providerKey
                    isLoading.value = false
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
            keyStore.save(KeyStoreKeys.AppLanguage, langCode)
        }
    }

    override fun setSimilarity(value: String) {
        val newValue = (value.toIntOrNull() ?: 75).coerceIn(0, 100)
        similarityThreshold.value = newValue.toString()
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.SimilarityThreshold, newValue)
        }
    }

    override fun setSpellcheckEnabled(value: Boolean) {
        spellcheckEnabled.value = value
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.SpellcheckEnabled, value)
        }
    }

    override fun setMachineTranslationProvider(index: Int) {
        if (index !in MachineTranslationRepository.AVAILABLE_PROVIDERS.indices) return

        val provider = MachineTranslationRepository.AVAILABLE_PROVIDERS[index]
        currentMachineTranslationProvider.value = provider
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.MachineTranslationProvider, index)
        }
    }

    override fun setMachineTranslationKey(value: String) {
        machineTranslationKey.value = value
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
        val provider = currentMachineTranslationProvider.value ?: return
        viewModelScope.launch(dispatchers.io) {
            isLoading.value = true
            val key = machineTranslationRepository.generateKey(
                provider = provider,
                username = username,
                password = password,
            )
            setMachineTranslationKey(key)
            isLoading.value = false
        }
    }
}
