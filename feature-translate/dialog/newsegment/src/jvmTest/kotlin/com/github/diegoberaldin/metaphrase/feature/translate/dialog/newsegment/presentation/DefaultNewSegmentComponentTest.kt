package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DefaultNewSegmentComponentTest {
    companion object {
        private val setup by lazy {
            startKoin {
                modules(localizationModule)
            }
            L10n.setLanguage("en")
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val sut = DefaultNewSegmentComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
    )

    init {
        setup
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyFieldsThenStateIsError() = runTest {
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns null
        lifecycle.create()
        sut.language = LanguageModel(code = "en")

        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.keyError)
        assertEquals("message_missing_field".localized(), uiState.textError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyKeyThenStateIsError() = runTest {
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns null
        lifecycle.create()
        sut.language = LanguageModel(code = "en")
        sut.setText("test")

        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.keyError)
        assertEquals("", uiState.textError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyTextThenStateIsError() = runTest {
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns null
        lifecycle.create()
        sut.language = LanguageModel(code = "en")
        sut.setKey("test")

        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("", uiState.keyError)
        assertEquals("message_missing_field".localized(), uiState.textError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithDuplicateKeyThenStateIsError() = runTest {
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        lifecycle.create()
        sut.language = LanguageModel(code = "en")
        sut.setKey("key")
        sut.setText("test")

        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("message_duplicate_key".localized(), uiState.keyError)
        assertEquals("", uiState.textError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithValidDataThenStateIsError() = runTest {
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns null
        coEvery { mockSegmentRepository.create(any(), any()) } returns 1
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(LanguageModel(id = 1, code = "en"))
        lifecycle.create()
        sut.language = LanguageModel(code = "en")
        sut.setKey("key")
        sut.setText("test")

        launch {
            sut.submit()
        }

        val uiState = sut.uiState.value
        assertEquals("", uiState.keyError)
        assertEquals("", uiState.textError)

        sut.done.test {
            val item = awaitItem()
            assertNotNull(item)
            assertEquals(1, item.id)
            assertEquals("key", item.key)
        }
    }
}
