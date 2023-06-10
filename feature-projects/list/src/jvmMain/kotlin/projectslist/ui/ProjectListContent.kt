package projectslist.ui

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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Token
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.ui.components.CustomTooltipArea
import common.ui.theme.SelectedBackground
import common.ui.theme.Spacing
import localized
import projectslist.presentation.ProjectListComponent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectsListContent(
    component: ProjectListComponent,
) {
    val uiState by component.uiState.collectAsState()
    Column(
        modifier = Modifier.padding(horizontal = Spacing.s, vertical = Spacing.m),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Text(
            text = "app_intro".localized(),
            style = MaterialTheme.typography.h5,
        )
        Spacer(modifier = Modifier.height(Spacing.s))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            items(uiState.projects) { item ->
                Row(
                    modifier = Modifier.background(
                        color = SelectedBackground,
                        shape = RoundedCornerShape(4.dp),
                    )
                        .padding(horizontal = Spacing.s, vertical = Spacing.lHalf)
                        .onClick {
                            component.openProject(item)
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
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CustomTooltipArea(
                        text = "tooltip_delete".localized(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(2.dp)
                                .onClick {
                                    component.delete(item)
                                },
                        )
                    }
                }
            }
        }
    }
}
