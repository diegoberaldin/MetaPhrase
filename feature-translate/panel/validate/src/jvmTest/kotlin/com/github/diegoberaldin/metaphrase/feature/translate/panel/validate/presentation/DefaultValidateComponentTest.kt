package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data.ValidationContent
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

class DefaultValidateComponentTest {
    private val lifecycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val sut = DefaultValidateComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
    )

    @Test
    fun givenComponentCreatedWhenLoadPlaceholderValidationThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        val languageIdSlot = slot<Int>()
        coEvery { mockSegmentRepository.getByKey(any(), capture(languageIdSlot)) } answers {
            when (languageIdSlot.captured) {
                1 -> SegmentModel(key = "key", text = "%1\$d units")
                else -> SegmentModel(key = "key", text = "%1\$s unità")
            }
        }
        lifecycle.create()
        sut.reduce(
            ValidateComponent.Intent.LoadInvalidPlaceholders(
                projectId = 1,
                languageId = 2,
                invalidKeys = listOf("key"),
            ),
        )

        val uiState = sut.uiState.value
        val content = uiState.content
        assertIs<ValidationContent.InvalidPlaceholders>(content)
        assertEquals(1, content.references.size)
        val reference = content.references.first()
        assertEquals("key", reference.key)
//        assertEquals(listOf("%1\$d"), reference.missingPlaceholders)
//        assertEquals(listOf("%1\$s"), reference.extraPlaceholders)
    }

    @Test
    fun givenComponentWithPlaceholderErrorsWhenClearedThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        val languageIdSlot = slot<Int>()
        coEvery { mockSegmentRepository.getByKey(any(), capture(languageIdSlot)) } answers {
            when (languageIdSlot.captured) {
                1 -> SegmentModel(key = "key", text = "%1\$d units")
                else -> SegmentModel(key = "key", text = "%1\$s unità")
            }
        }
        lifecycle.create()
        sut.reduce(
            ValidateComponent.Intent.LoadInvalidPlaceholders(
                projectId = 1,
                languageId = 2,
                invalidKeys = listOf("key"),
            ),
        )

        sut.reduce(ValidateComponent.Intent.Clear)

        val uiState = sut.uiState.value
        assertNull(uiState.content)
    }

    @Test
    fun givenComponentWithPlaceholderErrorsWhenSelectItemThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 1, code = "en", isBase = true)
        val languageIdSlot = slot<Int>()
        coEvery { mockSegmentRepository.getByKey(any(), capture(languageIdSlot)) } answers {
            when (languageIdSlot.captured) {
                1 -> SegmentModel(key = "key", text = "%1\$d units")
                else -> SegmentModel(key = "key", text = "%1\$s unità")
            }
        }
        lifecycle.create()
        sut.reduce(
            ValidateComponent.Intent.LoadInvalidPlaceholders(
                projectId = 1,
                languageId = 2,
                invalidKeys = listOf("key"),
            ),
        )

        sut.effects.test {
            sut.reduce(ValidateComponent.Intent.SelectItem(0))
            val item = awaitItem()
            assertIs<ValidateComponent.Effect.Selection>(item)
            assertEquals("key", item.key)
        }
    }

    @Test
    fun givenComponentCreatedWhenLoadSpellingValidationThenStateIsUpdated() = runTest {
        lifecycle.create()

        sut.reduce(ValidateComponent.Intent.LoadSpellingMistakes(errors = mapOf("key" to listOf("error"))))

        val uiState = sut.uiState.value
        val content = uiState.content
        assertIs<ValidationContent.SpellingMistakes>(content)
        assertEquals(1, content.references.size)
        val reference = content.references.first()
        assertEquals("key", reference.key)
    }

    @Test
    fun givenComponentWithSpellingErrorsWhenClearedThenStateIsUpdated() = runTest {
        lifecycle.create()
        sut.reduce(ValidateComponent.Intent.LoadSpellingMistakes(errors = mapOf("key" to listOf("error"))))

        sut.reduce(ValidateComponent.Intent.Clear)

        val uiState = sut.uiState.value
        assertNull(uiState.content)
    }

    @Test
    fun givenComponentWithSpellingErrorsWhenSelectItemThenStateIsUpdated() = runTest {
        lifecycle.create()
        sut.reduce(ValidateComponent.Intent.LoadSpellingMistakes(errors = mapOf("key" to listOf("error"))))

        sut.effects.test {
            sut.reduce(ValidateComponent.Intent.SelectItem(0))
            val item = awaitItem()
            assertIs<ValidateComponent.Effect.Selection>(item)
            assertEquals("key", item.key)
        }
    }
}
