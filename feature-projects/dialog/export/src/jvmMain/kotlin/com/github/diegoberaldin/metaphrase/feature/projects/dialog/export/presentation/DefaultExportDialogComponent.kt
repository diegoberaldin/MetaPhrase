package com.github.diegoberaldin.metaphrase.feature.projects.dialog.export.presentation

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
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.formats.ExportResourcesV2UseCase
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultExportDialogComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<ExportDialogComponent.Intent, ExportDialogComponent.UiState, ExportDialogComponent.Effect> = DefaultMviModel(
        ExportDialogComponent.UiState(),
    ),
    private val languageRepository: LanguageRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
    private val exportResources: ExportResourcesV2UseCase,
    private val segmentRepository: SegmentRepository,
) : ExportDialogComponent,
    MviModel<ExportDialogComponent.Intent, ExportDialogComponent.UiState, ExportDialogComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope
    private val dialogNavigation = SlotNavigation<ExportDialogComponent.DialogConfig>()

    override var projectId: Int = 0
    override val dialog: Value<ChildSlot<ExportDialogComponent.DialogConfig, *>> = childSlot(
        source = dialogNavigation,
        key = "ExportDialogSlot",
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

    override fun reduce(intent: ExportDialogComponent.Intent) {
        when (intent) {
            is ExportDialogComponent.Intent.AddLanguage -> addLanguage(intent.value)
            is ExportDialogComponent.Intent.RemoveLanguage -> removeLanguage(intent.value)
            is ExportDialogComponent.Intent.SelectType -> selectResourceType(intent.value)
            is ExportDialogComponent.Intent.SetOutputPath -> setOutputPath(intent.value)
            is ExportDialogComponent.Intent.OpenFileDialog -> openFileDialog()
            is ExportDialogComponent.Intent.CloseDialog -> closeDialog()
            is ExportDialogComponent.Intent.Submit -> submit()
        }
    }

    private fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(ExportDialogComponent.DialogConfig.None)
        }
    }

    private fun openFileDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(ExportDialogComponent.DialogConfig.SelectOutputFile)
        }
    }

    private fun loadLanguages() {
        viewModelScope.launch(dispatchers.io) {
            val languages = languageRepository.getAll(projectId).map { completeLanguage(it) }
            mvi.updateState {
                it.copy(
                    availableLanguages = languages,
                    selectedLanguages = languages,
                )
            }
        }
    }

    private fun addLanguage(value: LanguageModel) {
        mvi.updateState {
            it.copy(
                selectedLanguages = it.selectedLanguages + value,
            )
        }
    }

    private fun removeLanguage(value: LanguageModel) {
        mvi.updateState {
            it.copy(
                selectedLanguages = it.selectedLanguages - value,
            )
        }
    }

    private fun selectResourceType(value: ResourceFileType) {
        mvi.updateState {
            it.copy(
                selectedResourceType = value,
            )
        }
    }

    private fun setOutputPath(value: String) {
        mvi.updateState {
            it.copy(
                outputPath = value,
            )
        }
    }

    private fun submit() {
        mvi.updateState {
            it.copy(
                languagesError = "",
                resourceTypeError = "",
                outputPathError = "",
            )
        }
        var valid = true
        val currentStateValue = mvi.uiState.value
        if (currentStateValue.selectedLanguages.isEmpty()) {
            mvi.updateState { it.copy(languagesError = "message_select_one_language".localized()) }
            valid = false
        }
        if (currentStateValue.selectedResourceType == null) {
            mvi.updateState { it.copy(resourceTypeError = "message_missing_field".localized()) }
            valid = false
        }
        if (currentStateValue.outputPath.isEmpty()) {
            mvi.updateState { it.copy(outputPathError = "message_missing_field".localized()) }
            valid = false
        }
        if (!valid) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            mvi.updateState {
                it.copy(isLoading = true)
            }

            val segments = currentStateValue.selectedLanguages.associate { lang ->
                val languageId = languageRepository.getByCode(lang.code, projectId)?.id ?: 0
                lang.code to segmentRepository.getAll(languageId)
            }

            exportResources(
                type = currentStateValue.selectedResourceType ?: ResourceFileType.ANDROID_XML,
                path = currentStateValue.outputPath,
                data = segments,
            )

            mvi.updateState {
                it.copy(isLoading = false)
            }
            mvi.emitEffect(ExportDialogComponent.Effect.Done)
        }
    }
}
