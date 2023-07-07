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
import kotlin.test.assertIs
import kotlin.test.assertNotNull

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
        sut.reduce(NewGlossaryTermComponent.Intent.Submit)

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.sourceTermError)
        assertEquals("message_missing_field".localized(), uiState.targetTermError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyTargetThenStateIsError() = runTest {
        lifecycle.create()
        sut.reduce(NewGlossaryTermComponent.Intent.SetSourceTerm("test"))

        sut.reduce(NewGlossaryTermComponent.Intent.Submit)

        val uiState = sut.uiState.value
        assertEquals("", uiState.sourceTermError)
        assertEquals("message_missing_field".localized(), uiState.targetTermError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptySourceThenStateIsError() = runTest {
        lifecycle.create()
        sut.reduce(NewGlossaryTermComponent.Intent.SetTargetTerm("test"))

        sut.reduce(NewGlossaryTermComponent.Intent.Submit)

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.sourceTermError)
        assertEquals("", uiState.targetTermError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithValidDataTheDoneIsEmitted() = runTest {
        lifecycle.create()
        sut.reduce(NewGlossaryTermComponent.Intent.SetSourceTerm("test source"))
        sut.reduce(NewGlossaryTermComponent.Intent.SetTargetTerm("test target"))

        sut.effects.test {
            sut.reduce(NewGlossaryTermComponent.Intent.Submit)
            val item = awaitItem()
            assertIs<NewGlossaryTermComponent.Effect.Done>(item)
            val pair = item.pair
            assertNotNull(pair)
            assertEquals("test source", pair.sourceLemma)
            assertEquals("test target", pair.targetLemma)
        }

        val uiState = sut.uiState.value
        assertEquals("", uiState.sourceTermError)
        assertEquals("", uiState.targetTermError)
    }
}
