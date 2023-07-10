package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.main.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.asFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.presentation.AppearanceSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.presentation.GeneralSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.presentation.MachineTranslationSettingsComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

internal class DefaultSettingsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<SettingsComponent.Intent, SettingsComponent.UiState, SettingsComponent.Effect> = DefaultMviModel(
        SettingsComponent.UiState(),
    ),

) : SettingsComponent,
    MviModel<SettingsComponent.Intent, SettingsComponent.UiState, SettingsComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope
    private val contentNavigation = SlotNavigation<SettingsComponent.ContentConfig>()

    override val config: Value<ChildSlot<SettingsComponent.ContentConfig, *>> = childSlot(
        source = contentNavigation,
        key = "SettingsContentSlot",
        childFactory = { config, context ->
            when (config) {
                SettingsComponent.ContentConfig.General -> getByInjection<GeneralSettingsComponent>(
                    context,
                    coroutineContext,
                )

                SettingsComponent.ContentConfig.Appearance -> getByInjection<AppearanceSettingsComponent>(
                    context,
                    coroutineContext,
                )

                SettingsComponent.ContentConfig.MachineTranslation -> getByInjection<MachineTranslationSettingsComponent>(
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
                viewModelScope.launch(dispatchers.main) {
                    contentNavigation.activate(SettingsComponent.ContentConfig.General)
                }
                viewModelScope.launch {
                    config.asFlow<Any>(timeout = Duration.INFINITE).distinctUntilChanged().onEach {
                        when (it) {
                            is GeneralSettingsComponent -> {
                                it.uiState
                                    .map { s -> s.isLoading }
                                    .onEach { l -> mvi.updateState { s -> s.copy(isLoading = l) } }.launchIn(this)
                            }

                            is AppearanceSettingsComponent -> {
                                it.uiState
                                    .map { s -> s.isLoading }
                                    .onEach { l -> mvi.updateState { s -> s.copy(isLoading = l) } }.launchIn(this)
                            }

                            is MachineTranslationSettingsComponent -> {
                                it.uiState
                                    .map { s -> s.isLoading }
                                    .onEach { l -> mvi.updateState { s -> s.copy(isLoading = l) } }.launchIn(this)
                            }
                        }
                    }.launchIn(this)
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: SettingsComponent.Intent) {
        when (intent) {
            is SettingsComponent.Intent.ChangeTab -> changeTab(intent.index)
        }
    }

    private fun changeTab(index: Int) {
        mvi.updateState { it.copy(currentTab = index) }
        when (index) {
            0 -> contentNavigation.activate(SettingsComponent.ContentConfig.General)
            1 -> contentNavigation.activate(SettingsComponent.ContentConfig.Appearance)
            2 -> contentNavigation.activate(SettingsComponent.ContentConfig.MachineTranslation)
        }
    }
}
