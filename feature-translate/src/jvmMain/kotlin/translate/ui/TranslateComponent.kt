package translate.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import common.utils.getByInjection
import data.LanguageModel
import data.ResourceFileType
import kotlinx.coroutines.flow.StateFlow
import translate.ui.messagelist.MessageListComponent
import translate.ui.toolbar.TranslateToolbarComponent
import kotlin.coroutines.CoroutineContext

interface TranslateComponent {

    val toolbar: Value<ChildSlot<ToolbarConfig, TranslateToolbarComponent>>
    val messageList: Value<ChildSlot<MessageListConfig, MessageListComponent>>
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

    object Factory {
        fun create(
            componentContext: ComponentContext,
            coroutineContext: CoroutineContext,
        ): TranslateComponent = DefaultTranslateComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            dispatchers = getByInjection(),
            projectRepository = getByInjection(),
            languageRepository = getByInjection(),
            segmentRepository = getByInjection(),
            parseAndroidResources = getByInjection(),
            parseIosResources = getByInjection(),
            importSegments = getByInjection(),
            exportAndroidResources = getByInjection(),
            exportIosResources = getByInjection(),
        )
    }

    @Parcelize
    object ToolbarConfig : Parcelable

    @Parcelize
    object MessageListConfig : Parcelable
}
