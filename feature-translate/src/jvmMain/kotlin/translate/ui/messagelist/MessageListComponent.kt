package translate.ui.messagelist

import com.arkivanov.decompose.ComponentContext
import common.utils.getByInjection
import data.LanguageModel
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface MessageListComponent {

    val uiState: StateFlow<MessageListUiState>

    fun loadLanguage(language: LanguageModel, projectId: Int)

    fun startEditing(index: Int)

    fun endEditing()
    fun moveToPrevious()
    fun moveToNext()

    fun setSegmentText(text: String)

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
