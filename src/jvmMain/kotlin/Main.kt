import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuBarScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import common.di.commonModule
import common.keystore.TemporaryKeyStore
import common.log.LogManager
import common.ui.theme.MetaPhraseTheme
import common.utils.getByInjection
import common.utils.runOnUiThread
import projectdata.ResourceFileType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import main.di.mainModule
import main.presentation.RootComponent
import main.ui.RootContent
import org.koin.core.context.startKoin
import persistence.di.persistenceModule
import repository.di.projectModule
import java.util.*

private fun initKoin() {
    startKoin {
        modules(
            commonModule,
            persistenceModule,
            projectModule,
            mainModule,
        )
    }
}

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

    // init l10n
    runBlocking {
        val keystore: TemporaryKeyStore = getByInjection()
        val systemLanguage = Locale.getDefault().language
        val lang = keystore.get("lang", "")
        L10n.setLanguage(lang.ifEmpty { systemLanguage })
        if (lang.isEmpty()) {
            keystore.save("lang", "lang".localized())
        }
    }

    application {
        log.debug("Application starting")

        // ties component lifecycle to the window
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
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
                        .background(MaterialTheme.colors.background),
                ) {
                    RootContent(
                        component = rootComponent,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MenuBarScope.makeMenus(
    rootComponent: RootComponent,
) {
    val rootUiState by rootComponent.uiState.collectAsState()

    Menu(text = "menu_project".localized()) {
        Item(
            text = "menu_project_new".localized(),
            shortcut = KeyShortcut(Key.N, meta = true),
        ) {
            rootComponent.openNewDialog()
        }
        Item(
            text = "menu_project_edit".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            rootComponent.openEditProject()
        }
        Item(
            text = "menu_project_close".localized(),
            enabled = rootUiState.activeProject != null,
            shortcut = KeyShortcut(Key.W, meta = true),
        ) {
            rootComponent.closeCurrentProject()
        }
        Separator()
        Item(
            text = "menu_project_statistics".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            rootComponent.openStatistics()
        }
        Item(
            text = "menu_project_settings".localized(),
            shortcut = KeyShortcut(Key.Comma, meta = true),
        ) {
            rootComponent.openSettings()
        }
        Separator()
        Menu(
            text = "menu_project_import".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            Item(
                text = "menu_project_import_android".localized(),
            ) {
                rootComponent.openImportDialog(ResourceFileType.ANDROID_XML)
            }
            Item(
                text = "menu_project_import_ios".localized(),
            ) {
                rootComponent.openImportDialog(ResourceFileType.IOS_STRINGS)
            }
            Item(
                text = "menu_project_import_windows".localized(),
            ) {
                rootComponent.openImportDialog(ResourceFileType.RESX)
            }
            Item(
                text = "menu_project_import_po".localized(),
            ) {
                rootComponent.openImportDialog(ResourceFileType.PO)
            }
        }
        Menu(
            text = "menu_project_export".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            Item(
                text = "menu_project_import_android".localized(),
            ) {
                rootComponent.openExportDialog(ResourceFileType.ANDROID_XML)
            }
            Item(
                text = "menu_project_import_ios".localized(),
            ) {
                rootComponent.openExportDialog(ResourceFileType.IOS_STRINGS)
            }
            Item(
                text = "menu_project_import_windows".localized(),
            ) {
                rootComponent.openExportDialog(ResourceFileType.RESX)
            }
            Item(
                text = "menu_project_import_po".localized(),
            ) {
                rootComponent.openExportDialog(ResourceFileType.PO)
            }
        }
        Separator()
        Item(
            text = "menu_project_validate".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            rootComponent.validatePlaceholders()
        }
        Item(
            text = "menu_project_spellcheck".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            rootComponent.globalSpellcheck()
        }
    }
    Menu(
        text = "menu_segment".localized(),
    ) {
        Item(
            text = "menu_segment_previous".localized(),
            shortcut = KeyShortcut(Key.P, meta = true, shift = true),
            enabled = rootUiState.isEditing,
        ) {
            rootComponent.moveToPreviousSegment()
        }
        Item(
            text = "menu_segment_next".localized(),
            shortcut = KeyShortcut(Key.N, meta = true, shift = true),
            enabled = rootUiState.isEditing,
        ) {
            rootComponent.moveToNextSegment()
        }
        Separator()
        Item(
            text = "menu_segment_new".localized(),
            shortcut = KeyShortcut(Key.Plus, meta = true),
            enabled = rootUiState.activeProject != null,
        ) {
            rootComponent.addSegment()
        }
        Item(
            text = "menu_segment_delete".localized(),
            shortcut = KeyShortcut(Key.Minus, meta = true),
            enabled = rootUiState.isEditing,
        ) {
            rootComponent.deleteSegment()
        }
        Item(
            text = "menu_segment_copy_base".localized(),
            shortcut = KeyShortcut(Key.B, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            rootComponent.copyBase()
        }
        Item(
            text = "menu_segment_end_edit".localized(),
            shortcut = KeyShortcut(Key.Escape),
            enabled = rootUiState.isEditing,
        ) {
            rootComponent.endEditing()
        }
    }
    Menu(
        text = "menu_translation_memory".localized(),
    ) {
        Item(
            text = "menu_translation_memory_insert_best_match".localized(),
            shortcut = KeyShortcut(Key.M, meta = true, shift = true),
            enabled = rootUiState.isEditing,
        ) {
            rootComponent.insertBestMatch()
        }
        Separator()
        Item(
            text = "menu_translation_memory_export".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            rootComponent.openExportTmxDialog()
        }
        Item(
            text = "menu_translation_memory_import".localized(),
        ) {
            rootComponent.openImportTmxDialog()
        }
        Separator()
        Item(
            text = "menu_translation_memory_sync_project".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            rootComponent.syncTm()
        }
        Item(
            text = "menu_translation_memory_clear".localized(),
        ) {
            rootComponent.clearTm()
        }
    }
}
