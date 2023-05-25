import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import data.TranslationUnitTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.local.LanguageRepository
import repository.local.SegmentRepository
import repository.usecase.GetCompleteLanguageUseCase
import kotlin.coroutines.CoroutineContext

class DefaultStatisticsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : StatisticsComponent, ComponentContext by componentContext {

    private var items = MutableStateFlow<List<StatisticsItem>>(emptyList())

    private lateinit var viewModelScope: CoroutineScope
    override lateinit var uiState: StateFlow<StatisticsUiState>
    override var projectId: Int = 0
        set(value) {
            field = value
            loadStatistics()
        }

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = items.map {
                    StatisticsUiState(items = it)
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = StatisticsUiState(),
                )
                loadStatistics()
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    private fun loadStatistics() {
        if (!this::viewModelScope.isInitialized) return
        viewModelScope.launch(dispatchers.io) {
            val languages = languageRepository.getAll(projectId).map { completeLanguage(it) }
            val baseLanguage = languageRepository.getBase(projectId) ?: return@launch
            items.update {
                buildList {
                    this += StatisticsItem.Header("dialog_statistics_section_general".localized())
                    this += StatisticsItem.TextRow(
                        title = "dialog_statistics_item_total_languages".localized(),
                        value = languages.count().toString(),
                    )
                    val totalMessageCount = segmentRepository.getAll(baseLanguage.id).count()
                    this += StatisticsItem.TextRow(
                        title = "dialog_statistics_item_total_messages".localized(),
                        value = totalMessageCount.toString(),
                    )
                    val translatableCount =
                        segmentRepository.search(baseLanguage.id, filter = TranslationUnitTypeFilter.TRANSLATABLE)
                            .count()
                    this += StatisticsItem.TextRow(
                        title = "dialog_statistics_item_translatable_messages".localized(),
                        value = translatableCount.toString(),
                    )

                    this += StatisticsItem.Divider

                    for (language in languages) {
                        this += StatisticsItem.LanguageHeader(language.name)
                        val untranslatedCount =
                            segmentRepository.search(language.id, filter = TranslationUnitTypeFilter.UNTRANSLATED)
                                .count()
                        val completionRate =
                            (translatableCount - untranslatedCount).coerceAtLeast(0).toFloat() / translatableCount
                        this += StatisticsItem.BarChartRow(
                            title = "dialog_statistics_item_completion_rate".localized(),
                            value = completionRate,
                        )
                    }
                }
            }
        }
    }
}
