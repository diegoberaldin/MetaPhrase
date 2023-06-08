package translate.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import common.ui.theme.Spacing
import localized
import org.jetbrains.skiko.Cursor
import panelglossary.ui.GlossaryComponent
import panelglossary.ui.GlossaryContent
import panelmatches.ui.TranslationMemoryComponent
import panelmatches.ui.TranslationMemoryContent
import panelmemory.ui.BrowseMemoryComponent
import panelmemory.ui.BrowseMemoryContent
import panelvalidate.ui.InvalidSegmentComponent
import panelvalidate.ui.ValidateContent
import translate.ui.TranslateComponent.PanelConfig
import translatemessages.ui.MessageListContent
import translatenewsegment.ui.NewSegmentComponent
import translatenewsegment.ui.NewSegmentDialog
import translatetoolbar.ui.TranslateToolbar

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
                    modifier = Modifier
                        .pointerHoverIcon(PointerIcon(Cursor(Cursor.N_RESIZE_CURSOR)))
                        .draggable(
                            state = draggableState,
                            orientation = Orientation.Vertical,
                            reverseDirection = true,
                        ),
                ) {
                    Divider()
                    Spacer(modifier = Modifier.height(Spacing.xs))
                }
                when (panelConfiguration) {
                    PanelConfig.Matches -> {
                        val childComponent = panel.child?.instance as TranslationMemoryComponent
                        TranslationMemoryContent(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            component = childComponent,
                            onMinify = { component.togglePanel(PanelConfig.Matches) },
                        )
                        LaunchedEffect(component) {
                            component.tryLoadSimilarities()
                        }
                    }

                    PanelConfig.Validation -> {
                        val childComponent = panel.child?.instance as InvalidSegmentComponent
                        ValidateContent(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            component = childComponent,
                            onMinify = { component.togglePanel(PanelConfig.Validation) },
                        )
                    }

                    PanelConfig.MemoryContent -> {
                        val childComponent = panel.child?.instance as BrowseMemoryComponent
                        BrowseMemoryContent(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            component = childComponent,
                            onMinify = { component.togglePanel(PanelConfig.MemoryContent) },
                        )
                    }

                    PanelConfig.Glossary -> {
                        val childComponent = panel.child?.instance as GlossaryComponent
                        GlossaryContent(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            component = childComponent,
                            onMinify = { component.togglePanel(PanelConfig.Glossary) },
                        )
                        LaunchedEffect(component) {
                            component.tryLoadGlossary()
                        }
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
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                PanelChip(
                    title = "panel_section_matches".localized(),
                    isActive = panel.child?.configuration == PanelConfig.Matches,
                ) {
                    component.togglePanel(PanelConfig.Matches)
                }
                PanelChip(
                    title = "panel_section_checks".localized(),
                    isActive = panel.child?.configuration == PanelConfig.Validation,
                ) {
                    component.togglePanel(PanelConfig.Validation)
                }
                PanelChip(
                    title = "panel_section_translation_memory".localized(),
                    isActive = panel.child?.configuration == PanelConfig.MemoryContent,
                ) {
                    component.togglePanel(PanelConfig.MemoryContent)
                }
                PanelChip(
                    title = "panel_section_glossary".localized(),
                    isActive = panel.child?.configuration == PanelConfig.Glossary,
                ) {
                    component.togglePanel(PanelConfig.Glossary)
                }
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
    when (child?.configuration) {
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

        else -> Unit
    }
}
