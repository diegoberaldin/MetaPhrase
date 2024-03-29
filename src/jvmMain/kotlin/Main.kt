import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuBarScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.log.LogManager
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.ThemeRepository
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.ThemeState
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.TranslationThemeRepository
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.core.common.utils.runOnUiThread
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.core.persistence.di.persistenceModule
import com.github.diegoberaldin.metaphrase.domain.language.di.languageModule
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.project.di.projectModule
import com.github.diegoberaldin.metaphrase.feature.main.di.mainModule
import com.github.diegoberaldin.metaphrase.feature.main.presentation.RootComponent
import com.github.diegoberaldin.metaphrase.feature.main.ui.RootContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import java.util.*

private fun initKoin() {
    startKoin {
        modules(
            commonModule,
            localizationModule,
            persistenceModule,
            languageModule,
            projectModule,
            mainModule,
        )
    }
}

/**
 * Main application entry point.
 */
@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    // init DI
    initKoin()

    val log: LogManager = getByInjection()
    Thread.setDefaultUncaughtExceptionHandler { t, e ->
        log.exception("Exception in ${t.name}", cause = e)
    }

    // init root component in the main thread outside the application lifecycle
    val lifecycle = LifecycleRegistry()
    val mainScope = CoroutineScope(SupervisorJob())
    val rootComponent = runOnUiThread {
        getByInjection<RootComponent>(
            DefaultComponentContext(lifecycle = lifecycle),
            mainScope.coroutineContext,
        )
    }

    runBlocking {
        // init l10n
        val keyStore: TemporaryKeyStore = getByInjection()
        val systemLanguage = Locale.getDefault().language
        val lang = keyStore.get(KeyStoreKeys.AppLanguage, "")
        L10n.setLanguage(lang.ifEmpty { systemLanguage })
        if (lang.isEmpty()) {
            keyStore.save(KeyStoreKeys.AppLanguage, "lang".localized())
        }

        // init machine translation
        val provider = keyStore.get(KeyStoreKeys.MachineTranslationProvider, 0).let {
            MachineTranslationRepository.AVAILABLE_PROVIDERS[it]
        }
        val machineTranslationRepository: MachineTranslationRepository = getByInjection()
        machineTranslationRepository.setProvider(provider)
    }

    application {
        log.debug("Application starting")

        // ties component lifecycle to the window
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        // theme management
        val useDarkTheme = isSystemInDarkTheme()
        runBlocking {
            val keyStore: TemporaryKeyStore = getByInjection()
            if (!keyStore.containsKey(KeyStoreKeys.DarkThemeEnabled)) {
                keyStore.save(KeyStoreKeys.DarkThemeEnabled, useDarkTheme)
            }
            val darkModeEnabled = keyStore.get(KeyStoreKeys.DarkThemeEnabled, false)
            val themeRepository: ThemeRepository = getByInjection()
            themeRepository.changeTheme(if (darkModeEnabled) ThemeState.Dark else ThemeState.Light)
            if (!keyStore.containsKey(KeyStoreKeys.TranslationEditorFontSize)) {
                keyStore.save(KeyStoreKeys.TranslationEditorFontSize, 14)
            }
            if (!keyStore.containsKey(KeyStoreKeys.TranslationEditorFontType)) {
                keyStore.save(KeyStoreKeys.TranslationEditorFontType, 0)
            }
            val translationThemeRepository: TranslationThemeRepository = getByInjection()
            val currentStyle = translationThemeRepository.textStyle.value
            val translationFontSize = keyStore.get(KeyStoreKeys.TranslationEditorFontSize, 14).sp
            val fontTypeIndex = keyStore.get(KeyStoreKeys.TranslationEditorFontType, 0)
            val availableFontFamilies = translationThemeRepository.getAvailableFontFamilies()
            val fontFamily = availableFontFamilies.getOrNull(fontTypeIndex) ?: availableFontFamilies.first()
            translationThemeRepository.changeTextStyle(
                currentStyle.copy(
                    fontFamily = fontFamily,
                    fontSize = translationFontSize,
                ),
            )
        }

        Window(
            onCloseRequest = {
                if (rootComponent.hasUnsavedChanges()) {
                    rootComponent.reduce(RootComponent.Intent.CloseCurrentProject(closeAfter = true))
                } else {
                    exitApplication()
                }
            },
            title = "app_name".localized(),
            state = windowState,
        ) {
            // needed to respond to l10n changes
            val lang by L10n.currentLanguage.collectAsState("lang".localized())
            LaunchedEffect(lang) {}

            MenuBar {
                makeMenus(rootComponent)
            }

            MetaPhraseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    RootContent(
                        component = rootComponent,
                        modifier = Modifier.fillMaxSize(),
                        onExitApplication = {
                            exitApplication()
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuBarScope.makeMenus(
    rootComponent: RootComponent,
) {
    projectMenu(rootComponent)
    messageMenu(rootComponent)
    resourcesMenu(rootComponent)
    helpMenu(rootComponent)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MenuBarScope.projectMenu(component: RootComponent) {
    val rootUiState by component.uiState.collectAsState()

    Menu(text = "menu_project".localized()) {
        Item(
            text = "menu_project_open".localized(),
            shortcut = KeyShortcut(Key.O, meta = true),
        ) {
            component.reduce(RootComponent.Intent.OpenDialog)
        }
        Item(
            text = "menu_project_new".localized(),
            shortcut = KeyShortcut(Key.N, meta = true),
        ) {
            component.reduce(RootComponent.Intent.OpenNewDialog)
        }
        Item(
            text = "menu_project_edit".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.reduce(RootComponent.Intent.OpenEditProject)
        }
        Item(
            text = "menu_project_save".localized(),
            enabled = rootUiState.isSaveEnabled,
            shortcut = KeyShortcut(Key.S, meta = true),
        ) {
            component.reduce(RootComponent.Intent.SaveCurrentProject)
        }
        Item(
            text = "menu_project_save_as".localized(),
            enabled = rootUiState.activeProject != null,
            shortcut = KeyShortcut(Key.S, meta = true, shift = true),
        ) {
            component.reduce(RootComponent.Intent.SaveProjectAs)
        }
        Item(
            text = "menu_project_close".localized(),
            enabled = rootUiState.activeProject != null,
            shortcut = KeyShortcut(Key.W, meta = true),
        ) {
            component.reduce(RootComponent.Intent.CloseCurrentProject())
        }
        Separator()
        Item(
            text = "menu_project_statistics".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.reduce(RootComponent.Intent.OpenStatistics)
        }
        Item(
            text = "menu_project_settings".localized(),
            shortcut = KeyShortcut(Key.Comma, meta = true),
        ) {
            component.reduce(RootComponent.Intent.OpenSettings)
        }
        Separator()
        Item(
            text = "menu_project_import".localized(),
            enabled = rootUiState.activeProject != null,
            shortcut = KeyShortcut(Key.I, meta = true),
        ) {
            component.reduce(RootComponent.Intent.OpenImportDialogV2)
        }
        Item(
            text = "menu_project_export".localized(),
            enabled = rootUiState.activeProject != null,
            shortcut = KeyShortcut(Key.E, meta = true),
        ) {
            component.reduce(RootComponent.Intent.OpenExportDialogV2)
        }
        Separator()
        Item(
            text = "menu_project_validate".localized(),
            enabled = rootUiState.activeProject != null && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.reduce(RootComponent.Intent.ValidatePlaceholders)
        }
        Item(
            text = "menu_project_spellcheck".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.reduce(RootComponent.Intent.GlobalSpellcheck)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MenuBarScope.messageMenu(component: RootComponent) {
    val rootUiState by component.uiState.collectAsState()
    Menu(
        text = "menu_segment".localized(),
    ) {
        Item(
            text = "menu_segment_previous".localized(),
            shortcut = KeyShortcut(Key.P, meta = true, shift = true),
            enabled = rootUiState.isEditing,
        ) {
            component.reduce(RootComponent.Intent.MoveToPreviousSegment)
        }
        Item(
            text = "menu_segment_next".localized(),
            shortcut = KeyShortcut(Key.N, meta = true, shift = true),
            enabled = rootUiState.isEditing,
        ) {
            component.reduce(RootComponent.Intent.MoveToNextSegment)
        }
        Separator()
        Item(
            text = "menu_segment_new".localized(),
            shortcut = KeyShortcut(Key.Plus, meta = true),
            enabled = rootUiState.activeProject != null,
        ) {
            component.reduce(RootComponent.Intent.AddSegment)
        }
        Item(
            text = "menu_segment_delete".localized(),
            shortcut = KeyShortcut(Key.Minus, meta = true),
            enabled = rootUiState.isEditing,
        ) {
            component.reduce(RootComponent.Intent.DeleteSegment)
        }
        Item(
            text = "menu_segment_copy_base".localized(),
            shortcut = KeyShortcut(Key.B, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.reduce(RootComponent.Intent.CopyBase)
        }
        Item(
            text = "menu_segment_end_edit".localized(),
            shortcut = KeyShortcut(Key.Escape),
            enabled = rootUiState.isEditing,
        ) {
            component.reduce(RootComponent.Intent.EndEditing)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MenuBarScope.resourcesMenu(component: RootComponent) {
    val rootUiState by component.uiState.collectAsState()
    Menu(
        text = "menu_resources".localized(),
    ) {
        Item(
            text = "menu_translation_memory_insert_best_match".localized(),
            shortcut = KeyShortcut(Key.M, meta = true, shift = true),
            enabled = rootUiState.isEditing,
        ) {
            component.reduce(RootComponent.Intent.InsertBestMatch)
        }
        Separator()
        Item(
            text = "menu_translation_memory_import".localized(),
        ) {
            component.reduce(RootComponent.Intent.OpenImportTmxDialog)
        }
        Item(
            text = "menu_translation_memory_export".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.reduce(RootComponent.Intent.OpenExportTmxDialog)
        }
        Separator()
        Item(
            text = "menu_translation_memory_sync_project".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.reduce(RootComponent.Intent.SyncTm)
        }
        Item(
            text = "menu_translation_memory_clear".localized(),
        ) {
            component.reduce(RootComponent.Intent.ClearTm)
        }
        Separator()
        Item(
            text = "menu_glossary_import".localized(),
        ) {
            component.reduce(RootComponent.Intent.OpenImportGlossaryDialog)
        }
        Item(
            text = "menu_glossary_export".localized(),
        ) {
            component.reduce(RootComponent.Intent.OpenExportGlossaryDialog)
        }
        Item(
            text = "menu_glossary_clear".localized(),
        ) {
            component.reduce(RootComponent.Intent.ClearGlossary)
        }
        Separator()
        Item(
            text = "menu_machine_translation_retrieve".localized(),
            shortcut = KeyShortcut(Key.J, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.reduce(RootComponent.Intent.MachineTranslationRetrieve)
        }
        Item(
            text = "menu_machine_translation_insert".localized(),
            shortcut = KeyShortcut(Key.K, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.reduce(RootComponent.Intent.MachineTranslationInsert)
        }
        Item(
            text = "menu_machine_translation_copy_translation".localized(),
            shortcut = KeyShortcut(Key.H, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false && rootUiState.machineTranslationSupportsContributions,
        ) {
            component.reduce(RootComponent.Intent.MachineTranslationCopyTarget)
        }
        Item(
            text = "menu_machine_translation_copy_share".localized(),
            shortcut = KeyShortcut(Key.L, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false && rootUiState.machineTranslationSupportsContributions,
        ) {
            component.reduce(RootComponent.Intent.MachineTranslationShare)
        }
        Item(
            text = "menu_machine_translation_contribute_memory".localized(),
            shortcut = KeyShortcut(Key.M, meta = true, shift = true),
            enabled = rootUiState.machineTranslationSupportsContributions,
        ) {
            component.reduce(RootComponent.Intent.MachineTranslationContributeTm)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MenuBarScope.helpMenu(component: RootComponent) {
    Menu(
        text = "menu_help".localized(),
    ) {
        Item(
            text = "menu_help_open_manual".localized(),
            shortcut = KeyShortcut(Key.M, meta = true, shift = true),
        ) {
            component.reduce(RootComponent.Intent.OpenManual)
        }
    }
}
