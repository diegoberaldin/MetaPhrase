package com.github.diegoberaldin.feature.main.settings.dialog.login.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.localization.localized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DefaultLoginComponentTest {
    companion object {
        val setup by lazy {
            startKoin {
                modules(
                    localizationModule,
                )
            }
            L10n.setLanguage("en")
        }
    }

    private val scope = CoroutineScope(SupervisorJob())
    private val lifecycle = LifecycleRegistry()
    private val sut = DefaultLoginComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = scope.coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    init {
        setup
    }

    @Test
    fun givenEmptyFieldsWhenSubmitThenErrorStateIsEmitted() = runTest {
        lifecycle.create()
        sut.reduce(LoginComponent.Intent.Submit)
        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.usernameError)
        assertEquals("message_missing_field".localized(), uiState.passwordError)
    }

    @Test
    fun givenEmptyUsernameFieldWhenSubmitThenErrorStateIsEmitted() = runTest {
        lifecycle.create()
        sut.reduce(LoginComponent.Intent.SetPassword("test"))
        sut.reduce(LoginComponent.Intent.Submit)
        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.usernameError)
        assertEquals("", uiState.passwordError)
    }

    @Test
    fun givenEmptyPasswordFieldWhenSubmitThenErrorStateIsEmitted() = runTest {
        lifecycle.create()
        sut.reduce(LoginComponent.Intent.SetUsername("test"))
        sut.reduce(LoginComponent.Intent.Submit)
        val uiState = sut.uiState.value
        assertEquals("", uiState.usernameError)
        assertEquals("message_missing_field".localized(), uiState.passwordError)
    }

    @Test
    fun givenFieldsCompletedWhenSubmitThenDoneIsEmitted() = runTest {
        lifecycle.create()
        sut.reduce(LoginComponent.Intent.SetUsername("test_user"))
        sut.reduce(LoginComponent.Intent.SetPassword("test_pass"))

        sut.effects.test {
            sut.reduce(LoginComponent.Intent.Submit)
            val item = awaitItem()
            assertIs<LoginComponent.Effect.Done>(item)
            assertEquals("test_user", item.username)
            assertEquals("test_pass", item.password)
        }
    }
}
