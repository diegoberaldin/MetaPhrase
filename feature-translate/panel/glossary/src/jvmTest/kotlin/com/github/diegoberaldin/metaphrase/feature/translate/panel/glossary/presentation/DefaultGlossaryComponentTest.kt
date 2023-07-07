package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.GetGlossaryTermsUseCase
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.FlagsRepository
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DefaultGlossaryComponentTest {
    private val lifecycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockFlagsRepository = mockk<FlagsRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockGlossaryTermRepository = mockk<GlossaryTermRepository>()
    private val mockGetGlossaryTerms = mockk<GetGlossaryTermsUseCase>()
    private val sut = DefaultGlossaryComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        flagsRepository = mockFlagsRepository,
        segmentRepository = mockSegmentRepository,
        glossaryTermRepository = mockGlossaryTermRepository,
        getGlossaryTerms = mockGetGlossaryTerms,
    )

    @Test
    fun givenComponentCreatedWhenLoadThenTermsAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockFlagsRepository.getFlag(any()) } returns "<flag>"
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "text")
        coEvery { mockGetGlossaryTerms.invoke(any(), any()) } returns listOf(GlossaryTermModel())
        coEvery { mockGlossaryTermRepository.getAssociated(any(), any()) } returns emptyList()
        lifecycle.create()

        sut.reduce(GlossaryComponent.ViewIntent.Load(key = "key", projectId = 1, languageId = 1))

        val uiState = sut.uiState.value
        assertEquals(1, uiState.terms.size)
    }

    @Test
    fun givenComponentCreatedWhenClearThenTermsAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockFlagsRepository.getFlag(any()) } returns "<flag>"
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "text")
        coEvery { mockGetGlossaryTerms.invoke(any(), any()) } returns listOf(GlossaryTermModel())
        coEvery { mockGlossaryTermRepository.getAssociated(any(), any()) } returns emptyList()
        lifecycle.create()

        sut.reduce(GlossaryComponent.ViewIntent.Load(key = "key", projectId = 1, languageId = 1))
        val stateBefore = sut.uiState.value
        assertEquals(1, stateBefore.terms.size)

        sut.reduce(GlossaryComponent.ViewIntent.Clear)

        val stateAfter = sut.uiState.value
        assertEquals(0, stateAfter.terms.size)
    }

    @Test
    fun givenComponentCreatedWhenAddSourceTermThenTermsAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockFlagsRepository.getFlag(any()) } returns "<flag>"
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "text")
        coEvery { mockGetGlossaryTerms.invoke(any(), any()) } returns listOf(GlossaryTermModel())
        coEvery { mockGlossaryTermRepository.getAssociated(any(), any()) } returns emptyList()
        coEvery { mockGlossaryTermRepository.get(any(), any()) } returns null
        coEvery { mockGlossaryTermRepository.create(any()) } returns 1
        lifecycle.create()
        sut.reduce(GlossaryComponent.ViewIntent.Load(key = "key", projectId = 1, languageId = 1))

        sut.reduce(GlossaryComponent.ViewIntent.AddSourceTerm("lemma"))

        coVerify { mockGlossaryTermRepository.create(withArg { assertEquals("lemma", it.lemma) }) }
    }

    @Test
    fun givenComponentCreatedWhenAddTargetTermThenTermsAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockFlagsRepository.getFlag(any()) } returns "<flag>"
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "text")
        coEvery { mockGetGlossaryTerms.invoke(any(), any()) } returns listOf(GlossaryTermModel())
        coEvery { mockGlossaryTermRepository.getAssociated(any(), any()) } returns emptyList()
        coEvery { mockGlossaryTermRepository.get(any(), any()) } returns null
        coEvery { mockGlossaryTermRepository.create(any()) } returns 1
        coEvery { mockGlossaryTermRepository.areAssociated(any(), any()) } returns false
        coEvery { mockGlossaryTermRepository.associate(any(), any()) } returns Unit
        lifecycle.create()
        sut.reduce(GlossaryComponent.ViewIntent.Load(key = "key", projectId = 1, languageId = 1))

        sut.reduce(GlossaryComponent.ViewIntent.AddTargetTerm("lemma", GlossaryTermModel(lemma = "lemma", lang = "en")))

        coVerify { mockGlossaryTermRepository.create(withArg { assertEquals("lemma", it.lemma) }) }
        coVerify { mockGlossaryTermRepository.associate(any(), any()) }
    }

    @Test
    fun givenComponentCreatedWhenDeleteTermThenTermsAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        coEvery { mockFlagsRepository.getFlag(any()) } returns "<flag>"
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "text")
        coEvery { mockGetGlossaryTerms.invoke(any(), any()) } returns listOf(GlossaryTermModel())
        coEvery { mockGlossaryTermRepository.getAssociated(any(), any()) } returns emptyList()
        coEvery { mockGlossaryTermRepository.get(any(), any()) } returns null
        coEvery { mockGlossaryTermRepository.delete(any()) } returns Unit
        lifecycle.create()
        sut.reduce(GlossaryComponent.ViewIntent.Load(key = "key", projectId = 1, languageId = 1))

        sut.reduce(GlossaryComponent.ViewIntent.DeleteTerm(GlossaryTermModel(lemma = "lemma")))

        coVerify { mockGlossaryTermRepository.delete(withArg { assertEquals("lemma", it.lemma) }) }
    }
}
