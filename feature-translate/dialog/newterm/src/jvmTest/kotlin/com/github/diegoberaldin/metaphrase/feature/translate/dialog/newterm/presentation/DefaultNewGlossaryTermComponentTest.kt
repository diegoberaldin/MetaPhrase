package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.localization.localized
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultNewGlossaryTermComponentTest {

    companion object {
        private val setup by lazy {
            startKoin {
                modules(localizationModule)
            }
            L10n.setLanguage("en")
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val sut = DefaultNewGlossaryTermComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    init {
        setup
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyFieldsThenStateIsError() = runTest {
        lifecycle.create()
        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.sourceTermError)
        assertEquals("message_missing_field".localized(), uiState.targetTermError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyTargetThenStateIsError() = runTest {
        lifecycle.create()
        sut.setSourceTerm("test")

        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("", uiState.sourceTermError)
        assertEquals("message_missing_field".localized(), uiState.targetTermError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptySourceThenStateIsError() = runTest {
        lifecycle.create()
        sut.setTargetTerm("test")

        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.sourceTermError)
        assertEquals("", uiState.targetTermError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithValidDataTheDoneIsEmitted() = runTest {
        lifecycle.create()
        sut.setSourceTerm("test source")
        sut.setTargetTerm("test target")

        sut.done.test {
            sut.submit()
            val item = awaitItem()
            assertEquals("test source", item.sourceLemma)
            assertEquals("test target", item.targetLemma)
        }

        val uiState = sut.uiState.value
        assertEquals("", uiState.sourceTermError)
        assertEquals("", uiState.targetTermError)
    }
}
