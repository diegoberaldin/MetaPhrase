package com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultStatisticsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<StatisticsComponent.Intent, StatisticsComponent.UiState, StatisticsComponent.Effect> = DefaultMviModel(
        StatisticsComponent.UiState(),
    ),
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val completeLanguage: GetCompleteLanguageUseCase,
) : StatisticsComponent,
    MviModel<StatisticsComponent.Intent, StatisticsComponent.UiState, StatisticsComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope
    override var projectId: Int = 0
        set(value) {
            field = value
            loadStatistics()
        }

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
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
            mvi.updateState {
                it.copy(
                    items = buildList {
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
                        val translatableSourceMessages =
                            segmentRepository.search(
                                baseLanguage.id,
                                filter = TranslationUnitTypeFilter.TRANSLATABLE,
                            )
                        val translatableCount = translatableSourceMessages.count()
                        this += StatisticsItem.TextRow(
                            title = "dialog_statistics_item_translatable_messages".localized(),
                            value = translatableCount.toString(),
                        )
                        val translatableWordCount = translatableSourceMessages.fold(0) { acc, message ->
                            val words = message.text.split(Regex("\\W+"))
                            acc + words.count()
                        }
                        this += StatisticsItem.TextRow(
                            title = "dialog_statistics_item_translatable_words".localized(),
                            value = translatableWordCount.toString(),
                        )

                        this += StatisticsItem.Divider
                        this += StatisticsItem.Header("dialog_statistics_section_languages".localized())

                        val otherLanguages = languages.filter { l -> l.code != baseLanguage.code }
                        for (language in otherLanguages) {
                            this += StatisticsItem.LanguageHeader(language.name)
                            val untranslatedCount =
                                segmentRepository.search(
                                    language.id,
                                    filter = TranslationUnitTypeFilter.UNTRANSLATED,
                                )
                                    .count()
                            val completionRate = if (translatableCount == 0) {
                                0.0f
                            } else {
                                (translatableCount - untranslatedCount).coerceAtLeast(0)
                                    .toFloat() / translatableCount
                            }
                            this += StatisticsItem.BarChartRow(
                                title = "dialog_statistics_item_completion_rate".localized(),
                                value = completionRate,
                            )
                        }
                    },
                )
            }
        }
    }
}
