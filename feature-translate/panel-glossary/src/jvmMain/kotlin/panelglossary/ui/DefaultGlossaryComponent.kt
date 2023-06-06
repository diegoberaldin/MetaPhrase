package panelglossary.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import data.GlossaryTermModel
import glossary.repo.GlossaryTermRepository
import glossary.usecase.GetGlossaryTermsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import language.repo.LanguageRepository
import repository.repo.SegmentRepository
import kotlin.coroutines.CoroutineContext

internal class DefaultGlossaryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val glossaryTermRepository: GlossaryTermRepository,
    private val getGlossaryTerms: GetGlossaryTermsUseCase,
) : GlossaryComponent, ComponentContext by componentContext {

    private val loading = MutableStateFlow(false)
    private val terms = MutableStateFlow<List<Pair<GlossaryTermModel, List<GlossaryTermModel>>>>(emptyList())
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<GlossaryUiState>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(loading, terms) { loading, terms ->
                    GlossaryUiState(
                        loading = loading,
                        terms = terms,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = GlossaryUiState(),
                )
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun clear() {
        terms.value = emptyList()
    }

    override fun loadGlossaryTerms(key: String, projectId: Int, languageId: Int) {
        viewModelScope.launch(dispatchers.io) {
            val sourceLanguage = languageRepository.getBase(projectId) ?: return@launch
            val targetLanguage = languageRepository.getById(languageId) ?: return@launch
            val sourceSegment = segmentRepository.getByKey(key, languageId = sourceLanguage.id)
            loading.value = true
            terms.value =
                getGlossaryTerms(
                    message = sourceSegment?.text.orEmpty(),
                    lang = sourceLanguage.code,
                ).map { model ->
                    val targetTerms = glossaryTermRepository.getAssociated(
                        model = model,
                        otherLang = targetLanguage.code,
                    )
                    model to targetTerms
                }
            loading.value = false
        }
    }
}
