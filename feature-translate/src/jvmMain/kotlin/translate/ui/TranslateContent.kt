package translate.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import common.ui.components.CustomDialog
import common.ui.theme.SelectedBackground
import common.ui.theme.Spacing
import localized
import translate.ui.TranslateComponent.PanelConfig
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
        var panelHeight by remember {
            mutableStateOf(180.dp)
        }
        val panelConfiguration = panel.child?.configuration
        if (panelConfiguration != PanelConfig.None) {

            val density = LocalDensity.current
            val draggableState = rememberDraggableState {
                val newHeight = panelHeight + (it / density.density).dp
                panelHeight = newHeight.coerceAtLeast(8.dp)
            }
            Column(modifier = Modifier.height(panelHeight)) {
                Column(
                    modifier = Modifier.draggable(
                        state = draggableState,
                        orientation = Orientation.Vertical,
                        reverseDirection = true
                    )
                ) {
                    Spacer(modifier = Modifier.height(Spacing.xxxs))
                    Divider()
                    Spacer(modifier = Modifier.height(Spacing.xs))
                }
                when (panelConfiguration) {
                    PanelConfig.TranslationMemory -> {
                        val childComponent = panel.child?.instance as TranslationMemoryComponent
                        TranslationMemoryContent(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            component = childComponent,
                            onMinify = { component.togglePanel(PanelConfig.TranslationMemory) }
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
                        .padding(vertical = Spacing.xs, horizontal = Spacing.s)
                        .onClick {
                            component.togglePanel(PanelConfig.TranslationMemory)
                        },
                    text = "panel_section_matches".localized(),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground.copy(
                        alpha = if (panel.child?.configuration == PanelConfig.TranslationMemory) 1f else 0.7f,
                    ),
                )
            }

            val project = uiState.project
            if (project != null) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = buildString {
                        append(project.name)
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
