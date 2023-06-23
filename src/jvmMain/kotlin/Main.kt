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
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.log.LogManager
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.core.common.utils.runOnUiThread
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.core.persistence.di.persistenceModule
import com.github.diegoberaldin.metaphrase.domain.language.di.languageModule
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.feature.main.di.mainModule
import com.github.diegoberaldin.metaphrase.feature.main.presentation.RootComponent
import com.github.diegoberaldin.metaphrase.feature.main.ui.RootContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import repository.di.projectModule
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
        val lang = keystore.get(KeyStoreKeys.AppLanguage, "")
        L10n.setLanguage(lang.ifEmpty { systemLanguage })
        if (lang.isEmpty()) {
            keystore.save(KeyStoreKeys.AppLanguage, "lang".localized())
        }
    }

    application {
        log.debug("Application starting")

        // ties component lifecycle to the window
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = {
                if (rootComponent.hasUnsavedChanges()) {
                    rootComponent.closeCurrentProject(closeAfter = true)
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
                        .background(MaterialTheme.colors.background),
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
            component.openDialog()
        }
        Item(
            text = "menu_project_new".localized(),
            shortcut = KeyShortcut(Key.N, meta = true),
        ) {
            component.openNewDialog()
        }
        Item(
            text = "menu_project_edit".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.openEditProject()
        }
        Item(
            text = "menu_project_save".localized(),
            enabled = rootUiState.isSaveEnabled,
            shortcut = KeyShortcut(Key.S, meta = true),
        ) {
            component.saveCurrentProject()
        }
        Item(
            text = "menu_project_save_as".localized(),
            enabled = rootUiState.activeProject != null,
            shortcut = KeyShortcut(Key.S, meta = true, shift = true),
        ) {
            component.saveProjectAs()
        }
        Item(
            text = "menu_project_close".localized(),
            enabled = rootUiState.activeProject != null,
            shortcut = KeyShortcut(Key.W, meta = true),
        ) {
            component.closeCurrentProject()
        }
        Separator()
        Item(
            text = "menu_project_statistics".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.openStatistics()
        }
        Item(
            text = "menu_project_settings".localized(),
            shortcut = KeyShortcut(Key.Comma, meta = true),
        ) {
            component.openSettings()
        }
        Separator()
        Menu(
            text = "menu_project_import".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            Item(
                text = "menu_project_import_android".localized(),
            ) {
                component.openImportDialog(ResourceFileType.ANDROID_XML)
            }
            Item(
                text = "menu_project_import_ios".localized(),
            ) {
                component.openImportDialog(ResourceFileType.IOS_STRINGS)
            }
            Item(
                text = "menu_project_import_windows".localized(),
            ) {
                component.openImportDialog(ResourceFileType.RESX)
            }
            Item(
                text = "menu_project_import_po".localized(),
            ) {
                component.openImportDialog(ResourceFileType.PO)
            }
            Item(
                text = "menu_project_import_json".localized(),
            ) {
                component.openImportDialog(ResourceFileType.JSON)
            }
            Item(
                text = "menu_project_import_arb".localized(),
            ) {
                component.openImportDialog(ResourceFileType.ARB)
            }
            Item(
                text = "menu_project_import_properties".localized(),
            ) {
                component.openImportDialog(ResourceFileType.PROPERTIES)
            }
        }
        Menu(
            text = "menu_project_export".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            Item(
                text = "menu_project_import_android".localized(),
            ) {
                component.openExportDialog(ResourceFileType.ANDROID_XML)
            }
            Item(
                text = "menu_project_import_ios".localized(),
            ) {
                component.openExportDialog(ResourceFileType.IOS_STRINGS)
            }
            Item(
                text = "menu_project_import_windows".localized(),
            ) {
                component.openExportDialog(ResourceFileType.RESX)
            }
            Item(
                text = "menu_project_import_po".localized(),
            ) {
                component.openExportDialog(ResourceFileType.PO)
            }
            Item(
                text = "menu_project_import_json".localized(),
            ) {
                component.openExportDialog(ResourceFileType.JSON)
            }
            Item(
                text = "menu_project_import_arb".localized(),
            ) {
                component.openExportDialog(ResourceFileType.ARB)
            }
            Item(
                text = "menu_project_import_properties".localized(),
            ) {
                component.openExportDialog(ResourceFileType.PROPERTIES)
            }
        }
        Separator()
        Item(
            text = "menu_project_validate".localized(),
            enabled = rootUiState.activeProject != null && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.validatePlaceholders()
        }
        Item(
            text = "menu_project_spellcheck".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.globalSpellcheck()
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
            component.moveToPreviousSegment()
        }
        Item(
            text = "menu_segment_next".localized(),
            shortcut = KeyShortcut(Key.N, meta = true, shift = true),
            enabled = rootUiState.isEditing,
        ) {
            component.moveToNextSegment()
        }
        Separator()
        Item(
            text = "menu_segment_new".localized(),
            shortcut = KeyShortcut(Key.Plus, meta = true),
            enabled = rootUiState.activeProject != null,
        ) {
            component.addSegment()
        }
        Item(
            text = "menu_segment_delete".localized(),
            shortcut = KeyShortcut(Key.Minus, meta = true),
            enabled = rootUiState.isEditing,
        ) {
            component.deleteSegment()
        }
        Item(
            text = "menu_segment_copy_base".localized(),
            shortcut = KeyShortcut(Key.B, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.copyBase()
        }
        Item(
            text = "menu_segment_end_edit".localized(),
            shortcut = KeyShortcut(Key.Escape),
            enabled = rootUiState.isEditing,
        ) {
            component.endEditing()
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
            component.insertBestMatch()
        }
        Separator()
        Item(
            text = "menu_translation_memory_import".localized(),
        ) {
            component.openImportTmxDialog()
        }
        Item(
            text = "menu_translation_memory_export".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.openExportTmxDialog()
        }
        Separator()
        Item(
            text = "menu_translation_memory_sync_project".localized(),
            enabled = rootUiState.activeProject != null,
        ) {
            component.syncTm()
        }
        Item(
            text = "menu_translation_memory_clear".localized(),
        ) {
            component.clearTm()
        }
        Separator()
        Item(
            text = "menu_glossary_import".localized(),
        ) {
            component.openImportGlossaryDialog()
        }
        Item(
            text = "menu_glossary_export".localized(),
        ) {
            component.openExportGlossaryDialog()
        }
        Item(
            text = "menu_glossary_clear".localized(),
        ) {
            component.clearGlossary()
        }
        Separator()
        Item(
            text = "menu_machine_translation_retrieve".localized(),
            shortcut = KeyShortcut(Key.J, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.machineTranslationRetrieve()
        }
        Item(
            text = "menu_machine_translation_insert".localized(),
            shortcut = KeyShortcut(Key.K, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.machineTranslationInsert()
        }
        Item(
            text = "menu_machine_translation_copy_translation".localized(),
            shortcut = KeyShortcut(Key.H, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.machineTranslationCopyTarget()
        }
        Item(
            text = "menu_machine_translation_copy_share".localized(),
            shortcut = KeyShortcut(Key.L, meta = true, shift = true),
            enabled = rootUiState.isEditing && rootUiState.currentLanguage?.isBase == false,
        ) {
            component.machineTranslationShare()
        }
        Item(
            text = "menu_machine_translation_contribute_memory".localized(),
            shortcut = KeyShortcut(Key.M, meta = true, shift = true),
        ) {
            component.machineTranslationContributeTm()
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
            component.openManual()
        }
    }
}
