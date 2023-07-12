package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.presentation

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
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DefaultMachineTranslationSettingsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<MachineTranslationSettingsComponent.Intent, MachineTranslationSettingsComponent.UiState, MachineTranslationSettingsComponent.Effect> = DefaultMviModel(
        MachineTranslationSettingsComponent.UiState(),
    ),
    private val keyStore: TemporaryKeyStore,
    private val machineTranslationRepository: MachineTranslationRepository,
) : MachineTranslationSettingsComponent,
    MviModel<MachineTranslationSettingsComponent.Intent, MachineTranslationSettingsComponent.UiState, MachineTranslationSettingsComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope
    private val dialogNavigation = SlotNavigation<MachineTranslationSettingsComponent.DialogConfig>()

    override val dialog: Value<ChildSlot<MachineTranslationSettingsComponent.DialogConfig, *>> = childSlot(
        source = dialogNavigation,
        key = "GeneralSettingsComponentDialogSlot",
        childFactory = { config, context ->
            when (config) {
                MachineTranslationSettingsComponent.DialogConfig.Login -> getByInjection<LoginComponent>(
                    context,
                    coroutineContext,
                )

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
                    val providerIndex = keyStore.get(KeyStoreKeys.MachineTranslationProvider, 0)
                    val providerKey = keyStore.get(KeyStoreKeys.MachineTranslationKey, "")
                    val currentProvider = MachineTranslationRepository.AVAILABLE_PROVIDERS[providerIndex]
                    mvi.updateState {
                        it.copy(
                            availableProviders = MachineTranslationRepository.AVAILABLE_PROVIDERS,
                            currentProvider = currentProvider,
                            key = providerKey,
                            isLoading = false,
                        )
                    }
                    machineTranslationRepository.supportsKeyGeneration.onEach { supported ->
                        mvi.updateState {
                            it.copy(
                                supportsKeyGeneration = supported,
                            )
                        }
                    }.launchIn(viewModelScope)
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: MachineTranslationSettingsComponent.Intent) {
        when (intent) {
            is MachineTranslationSettingsComponent.Intent.SetMachineTranslationProvider -> setMachineTranslationProvider(
                intent.index,
            )

            is MachineTranslationSettingsComponent.Intent.SetMachineTranslationKey -> setMachineTranslationKey(intent.value)
            MachineTranslationSettingsComponent.Intent.OpenLoginDialog -> openLoginDialog()
            MachineTranslationSettingsComponent.Intent.CloseDialog -> closeDialog()
            is MachineTranslationSettingsComponent.Intent.GenerateMachineTranslationKey -> generateMachineTranslationKey(
                username = intent.username,
                password = intent.password,
            )
        }
    }

    private fun setMachineTranslationProvider(index: Int) {
        if (index !in MachineTranslationRepository.AVAILABLE_PROVIDERS.indices) return

        val provider = MachineTranslationRepository.AVAILABLE_PROVIDERS[index]
        machineTranslationRepository.setProvider(provider)
        // changing provider also invalidates the key
        mvi.updateState {
            it.copy(
                currentProvider = provider,
                key = "",
            )
        }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.MachineTranslationProvider, index)
            keyStore.save(KeyStoreKeys.MachineTranslationKey, "")
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
            dialogNavigation.activate(MachineTranslationSettingsComponent.DialogConfig.Login)
        }
    }

    private fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(MachineTranslationSettingsComponent.DialogConfig.None)
        }
    }

    private fun generateMachineTranslationKey(username: String, password: String) {
        viewModelScope.launch(dispatchers.io) {
            mvi.updateState { it.copy(isLoading = true) }
            val key = machineTranslationRepository.generateKey(
                username = username,
                password = password,
            )
            setMachineTranslationKey(key)
            mvi.updateState { it.copy(isLoading = false) }
        }
    }
}
