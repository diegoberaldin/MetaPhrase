package translatemessages.ui

import com.arkivanov.decompose.ComponentContext
import common.utils.getByInjection
import data.LanguageModel
import data.TranslationUnitTypeFilter
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface MessageListComponent {

    val uiState: StateFlow<MessageListUiState>
    val selectionEvents: SharedFlow<Int>

    fun reloadMessages(language: LanguageModel, filter: TranslationUnitTypeFilter, projectId: Int)
    fun refresh()
    fun search(text: String)
    fun startEditing(index: Int)
    fun endEditing()
    fun moveToPrevious()
    fun moveToNext()
    fun setSegmentText(text: String)
    fun copyBase()
    fun deleteSegment()
    fun scrollToMessage(key: String)
    fun markAsTranslatable(value: Boolean, key: String)

    object Factory {
        fun create(
            componentContext: ComponentContext,
            coroutineContext: CoroutineContext,
        ): MessageListComponent = DefaultMessageListComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            dispatchers = getByInjection(),
            segmentRepository = getByInjection(),
            languageRepository = getByInjection(),
        )
    }
}