package translate.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import common.ui.components.CustomDialog
import common.ui.theme.SelectedBackground
import common.ui.theme.Spacing
import localized
import translateinvalidsegments.ui.InvalidSegmentComponent
import translateinvalidsegments.ui.InvalidSegmentDialog
import translatemessages.ui.MessageListContent
import translatenewsegment.ui.NewSegmentComponent
import translatenewsegment.ui.NewSegmentDialog
import translatetoolbar.ui.TranslateToolbar
import translatetoolbar.ui.TranslateToolbarUiState
import translationtranslationmemory.ui.TranslationMemoryComponent
import translationtranslationmemory.ui.TranslationMemoryContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TranslateContent(
    component: TranslateComponent,
) {
    val uiState by component.uiState.collectAsState()
    val toolbar by component.toolbar.subscribeAsState()
    val panel by component.panel.subscribeAsState()

    Column(modifier = Modifier.fillMaxSize().padding(vertical = Spacing.xs, horizontal = Spacing.xxs)) {
        (toolbar.child?.instance)?.also {
            TranslateToolbar(
                component = it,
            )
        }

        val messageList by component.messageList.subscribeAsState()
        (messageList.child?.instance)?.also {
            MessageListContent(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                component = it,
            )
        }

        // panel
        val panelConfiguration = panel.child?.configuration
        if (panelConfiguration != TranslateComponent.PanelConfig.None) {
            Column(modifier = Modifier.height(180.dp)) {
                Divider()
                when (panelConfiguration) {
                    TranslateComponent.PanelConfig.TranslationMemory -> {
                        val childComponent = panel.child?.instance as TranslationMemoryComponent
                        TranslationMemoryContent(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            component = childComponent,
                        )
                    }

                    else -> Unit
                }
            }
        }

        // status bar
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Text(
                    modifier = Modifier.background(
                        color = SelectedBackground,
                        shape = RoundedCornerShape(4.dp),
                    )
                        .padding(vertical = Spacing.xxs, horizontal = Spacing.xs)
                        .onClick {
                            component.togglePanel(TranslateComponent.PanelConfig.TranslationMemory)
                        },
                    text = "panel_action_translation_memory".localized(),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground,
                )
            }

            val project = uiState.project
            if (project != null) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = buildString {
                        append("status_bar_project".localized(project.name))
                        append(" — ")
                        append("status_bar_units".localized(uiState.unitCount))
                    },
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }

    val dialogConfig by component.dialog.subscribeAsState()
    val child = dialogConfig.child
    when (val config = child?.configuration) {
        TranslateComponent.DialogConfig.NewSegment -> {
            val language by component.currentLanguage.collectAsState()
            val projectId = uiState.project?.id ?: 0
            val childComponent = child.instance as NewSegmentComponent
            language?.also {
                childComponent.language = it
            }
            childComponent.projectId = projectId
            NewSegmentDialog(component = childComponent)
        }

        TranslateComponent.DialogConfig.PlaceholderValid -> {
            CustomDialog(
                title = "dialog_title_generic_message".localized(),
                message = "message_validation_valid".localized(),
                closeButtonText = "button_close".localized(),
                onClose = {
                    component.closeDialog()
                },
            )
        }

        is TranslateComponent.DialogConfig.PlaceholderInvalid -> {
            val childComponent = child.instance as InvalidSegmentComponent
            val toolbarState = toolbar.child?.instance?.uiState?.collectAsState(TranslateToolbarUiState())
            childComponent.languageId = toolbarState?.value?.currentLanguage?.id ?: 0
            childComponent.projectId = uiState.project?.id ?: 0
            childComponent.invalidKeys = config.keys
            InvalidSegmentDialog(
                component = childComponent,
                onClose = {
                    component.closeDialog()
                },
            )
        }

        else -> Unit
    }
}
