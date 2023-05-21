package projectscreate.ui

import com.arkivanov.decompose.ComponentContext
import common.utils.getByInjection
import data.LanguageModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface CreateProjectComponent {

    var projectId: Int
    val uiState: StateFlow<CreateProjectUiState>
    val languagesUiState: StateFlow<CreateProjectLanguagesUiState>
    val done: SharedFlow<Int?>

    fun setName(value: String)
    fun addLanguage(value: LanguageModel)
    fun setBaseLanguage(value: LanguageModel)
    fun removeLanguage(value: LanguageModel)
    fun submit()

    object Factory {
        fun create(
            componentContext: ComponentContext,
            coroutineContext: CoroutineContext,
        ): CreateProjectComponent = DefaultCreateProjectComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            dispatchers = getByInjection(),
            languageRepository = getByInjection(),
            projectRepository = getByInjection(),
            completeLanguage = getByInjection(),
        )
    }
}
