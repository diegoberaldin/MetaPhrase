package translate.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import common.ui.theme.Spacing
import translate.ui.messagelist.MessageListContent
import translate.ui.toolbar.TranslateToolbar
import translatenewsegment.ui.NewSegmentComponent
import translatenewsegment.ui.NewSegmentDialog

@Composable
fun TranslateContent(
    component: TranslateComponent,
) {
    val uiState by component.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(vertical = Spacing.xs, horizontal = Spacing.xxs)) {
        val toolbar by component.toolbar.subscribeAsState()
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

        // status bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            val project = uiState.project
            if (project != null) {
                Text(
                    text = "project: ${project.name} — units: ${uiState.unitCount}",
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
