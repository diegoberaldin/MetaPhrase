package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.main.presentation

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.feature.main.settings.dialog.login.di.dialogLoginModule
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.runOnUiThread
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.domain.language.di.languageModule
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.di.appearanceSettingsModule
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.di.generalSettingsModule
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.di.machineTranslationSettingsModule
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultSettingsComponentTest {
    companion object {
        val setup by lazy {
            startKoin {
                modules(
                    commonModule,
                    localizationModule,
                    dialogLoginModule,
                    languageModule,
                    generalSettingsModule,
                    appearanceSettingsModule,
                    machineTranslationSettingsModule,
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun teardown() {
            stopKoin()
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val sut = DefaultSettingsComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    init {
        setup
    }

    @BeforeTest
    fun setup() {
        L10n.setLanguage("en")
    }

    @Test
    fun givenComponentCreatedWhenChangeTabThenStateIsUpdated() = runTest {
        runOnUiThread {
            lifecycle.create()
        }

        val stateBefore = sut.uiState.value
        assertEquals(0, stateBefore.currentTab)

        runOnUiThread {
            sut.reduce(SettingsComponent.Intent.ChangeTab(1))
        }
        val stateAfter = sut.uiState.value
        assertEquals(1, stateAfter.currentTab)
    }
}
