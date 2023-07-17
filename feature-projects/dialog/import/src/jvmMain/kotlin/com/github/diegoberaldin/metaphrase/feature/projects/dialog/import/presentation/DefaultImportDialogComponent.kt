package com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.formats.ImportResourcesV2UseCase
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultImportDialogComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<ImportDialogComponent.Intent, ImportDialogComponent.UiState, ImportDialogComponent.Effect> = DefaultMviModel(
        ImportDialogComponent.UiState(),
    ),
    private val languageRepository: LanguageRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
    private val importResources: ImportResourcesV2UseCase,
) : ImportDialogComponent,
    MviModel<ImportDialogComponent.Intent, ImportDialogComponent.UiState, ImportDialogComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private val dialogNavigation = SlotNavigation<ImportDialogComponent.DialogConfig>()
    private lateinit var viewModelScope: CoroutineScope

    override var projectId: Int = 0

    override val dialog = childSlot(
        source = dialogNavigation,
        key = "DialogImportDialogSlot",
        childFactory = { _, _ -> },
    )

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                mvi.updateState {
                    it.copy(
                        selectedResourceType = ResourceFileType.ANDROID_XML,
                        availableResourceTypes = listOf(
                            ResourceFileType.ANDROID_XML,
                            ResourceFileType.IOS_STRINGS,
                            ResourceFileType.RESX,
                            ResourceFileType.JSON,
                            ResourceFileType.ARB,
                            ResourceFileType.PO,
                            ResourceFileType.PROPERTIES,
                        ),
                    )
                }
                loadLanguages()
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: ImportDialogComponent.Intent) {
        when (intent) {
            ImportDialogComponent.Intent.CloseDialog -> closeDialog()
            is ImportDialogComponent.Intent.OpenFileDialog -> openFileDialog(intent.lang)
            is ImportDialogComponent.Intent.SelectType -> selectResourceFileType(intent.value)
            is ImportDialogComponent.Intent.SetInputPath -> setInputPath(lang = intent.lang, path = intent.path)
            ImportDialogComponent.Intent.Submit -> submit()
        }
    }

    private fun loadLanguages() {
        viewModelScope.launch(dispatchers.io) {
            val languageMap = languageRepository.getAll(projectId).map {
                completeLanguage(it)
            }.associateWith { "" }
            mvi.updateState {
                it.copy(languages = languageMap)
            }
        }
    }

    private fun openFileDialog(lang: String) {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(ImportDialogComponent.DialogConfig.OpenFile(lang))
        }
    }

    private fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(ImportDialogComponent.DialogConfig.None)
        }
    }

    private fun selectResourceFileType(value: ResourceFileType) {
        mvi.updateState { it.copy(selectedResourceType = value) }
    }

    private fun setInputPath(lang: String, path: String) {
        val oldMap = uiState.value.languages
        val key = oldMap.keys.firstOrNull { it.code == lang }
        if (key != null) {
            val newMap = oldMap.toMutableMap().apply {
                this[key] = path
            }
            mvi.updateState { it.copy(languages = newMap) }
        }
    }

    private fun submit() {
        val currentState = uiState.value
        var valid = true
        if (currentState.selectedResourceType == null) {
            valid = false
            mvi.updateState { it.copy(resourceTypeError = "message_missing_field".localized()) }
        }
        val languagesToExport = currentState.languages.filter { it.value.isNotEmpty() }
        if (languagesToExport.isEmpty()) {
            valid = false
            mvi.updateState { it.copy(languagesError = "Please select at least one file".localized()) }
        }
        if (!valid) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            mvi.updateState { it.copy(isLoading = true) }
            importResources(
                projectId = projectId,
                paths = languagesToExport.mapKeys { it.key.code },
                type = currentState.selectedResourceType ?: ResourceFileType.ANDROID_XML,
            )
            mvi.updateState { it.copy(isLoading = false) }
            mvi.emitEffect(ImportDialogComponent.Effect.Done)
        }
    }
}
