package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.MessageListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.TranslateToolbarComponent
import kotlinx.coroutines.flow.StateFlow

interface TranslateComponent {

    val toolbar: Value<ChildSlot<ToolbarConfig, TranslateToolbarComponent>>
    val messageList: Value<ChildSlot<MessageListConfig, MessageListComponent>>
    val dialog: Value<ChildSlot<DialogConfig, *>>
    val panel: Value<ChildSlot<PanelConfig, *>>
    val uiState: StateFlow<TranslateUiState>
    var projectId: Int
    val isEditing: StateFlow<Boolean>
    val currentLanguage: StateFlow<LanguageModel?>

    fun save(path: String)
    fun import(path: String, type: ResourceFileType)
    fun export(path: String, type: ResourceFileType)
    fun moveToPrevious()
    fun moveToNext()
    fun endEditing()
    fun copyBase()
    fun addSegment()
    fun deleteSegment()
    fun closeDialog()
    fun togglePanel(config: PanelConfig)
    fun tryLoadSimilarities()
    fun tryLoadGlossary()
    fun exportTmx(path: String)
    fun validatePlaceholders()
    fun insertBestMatch()
    fun globalSpellcheck()
    fun syncWithTm()
    fun addGlossaryTerm(source: String?, target: String?)

    @Parcelize
    object ToolbarConfig : Parcelable

    @Parcelize
    object MessageListConfig : Parcelable

    sealed interface DialogConfig : Parcelable {
        @Parcelize
        object None : DialogConfig

        @Parcelize
        object NewSegment : DialogConfig

        @Parcelize
        data class NewGlossaryTerm(val target: String) : DialogConfig
    }

    sealed interface PanelConfig : Parcelable {
        @Parcelize
        object None : PanelConfig

        @Parcelize
        object Matches : PanelConfig

        @Parcelize
        object Validation : PanelConfig

        @Parcelize
        object MemoryContent : PanelConfig

        @Parcelize
        object Glossary : PanelConfig
    }
}
