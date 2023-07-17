package com.github.diegoberaldin.metaphrase.feature.projects.dialog.statistics.presentation

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import kotlin.test.assertEquals

class DefaultStatisticsComponentTest {
    companion object {
        private val setup by lazy {
            startKoin { modules(localizationModule) }
            L10n.setLanguage("en")
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockCompleteLanguage = mockk<GetCompleteLanguageUseCase>()
    private val sut = DefaultStatisticsComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
        completeLanguage = mockCompleteLanguage,
    )

    init {
        setup
    }

    @Test
    fun givenComponentWhenCreatedThenStatisticsAreLoaded() {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", isBase = true),
            LanguageModel(code = "it"),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel())
        lifecycle.create()

        val uiState = sut.uiState.value
        val items = uiState.items
        assertEquals(2, items.filterIsInstance<StatisticsItem.Header>().size)
        assertEquals(9, items.size)
    }
}
