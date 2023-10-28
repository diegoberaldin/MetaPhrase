package com.github.diegoberaldin.metaphrase.feature.projects.list.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Token
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomDialog
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.projects.list.presentation.ProjectListComponent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectsListContent(
    component: ProjectListComponent,
) {
    val uiState by component.uiState.collectAsState()
    val dialog by component.dialog.subscribeAsState()
    Column(
        modifier = Modifier.padding(horizontal = Spacing.s, vertical = Spacing.m),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Text(
            text = "app_intro".localized(),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(Spacing.s))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            items(uiState.projects) { item ->
                Row(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp),
                    )
                        .padding(horizontal = Spacing.s, vertical = Spacing.lHalf)
                        .onClick {
                            component.reduce(ProjectListComponent.Intent.OpenRecent(item))
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s),
                ) {
                    Icon(
                        imageVector = Icons.Default.Token,
                        contentDescription = null,
                    )
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CustomTooltipArea(
                        text = "tooltip_remove_from_recent".localized(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(2.dp)
                                .onClick {
                                    component.reduce(ProjectListComponent.Intent.RemoveFromRecent(item))
                                },
                        )
                    }
                }
            }
        }
    }

    when (dialog.child?.configuration) {
        ProjectListComponent.DialogConfiguration.OpenError -> CustomDialog(
            title = "dialog_title_warning".localized(),
            message = "message_open_error".localized(),
            buttonTexts = listOf("button_close".localized()),
            onClose = {
                component.reduce(ProjectListComponent.Intent.CloseDialog)
            },
        )

        else -> Unit
    }
}
