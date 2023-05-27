package translate.ui

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import data.LanguageModel
import data.ResourceFileType
import kotlinx.coroutines.flow.StateFlow
import translatemessages.ui.MessageListComponent
import translatetoolbar.ui.TranslateToolbarComponent

interface TranslateComponent {

    val toolbar: Value<ChildSlot<ToolbarConfig, TranslateToolbarComponent>>
    val messageList: Value<ChildSlot<MessageListConfig, MessageListComponent>>
    val dialog: Value<ChildSlot<DialogConfig, *>>
    val uiState: StateFlow<TranslateUiState>
    var projectId: Int
    val isEditing: StateFlow<Boolean>
    val currentLanguage: StateFlow<LanguageModel?>

    fun import(path: String, type: ResourceFileType)
    fun export(path: String, type: ResourceFileType)
    fun moveToPrevious()
    fun moveToNext()
    fun endEditing()
    fun copyBase()
    fun addSegment()
    fun deleteSegment()
    fun closeDialog()

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
        object PlaceholderValid : DialogConfig

        @Parcelize
        data class PlaceholderInvalid(val keys: List<String>) : DialogConfig
    }
}
