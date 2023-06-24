package com.github.diegoberaldin.metaphrase.feature.projects.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.github.diegoberaldin.metaphrase.feature.projects.list.presentation.ProjectListComponent
import com.github.diegoberaldin.metaphrase.feature.projects.list.ui.ProjectsListContent
import com.github.diegoberaldin.metaphrase.feature.projects.presentation.ProjectsComponent
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent
import com.github.diegoberaldin.metaphrase.feature.translate.ui.TranslateContent

/**
 * UI content for the projects screen (either project list or translate editor).
 *
 * @param component Component
 */
@Composable
fun ProjectsContent(
    component: ProjectsComponent,
) {
    val state by component.childStack.subscribeAsState()

    when (state.active.configuration) {
        ProjectsComponent.Config.List -> {
            ProjectsListContent(component = state.active.instance as ProjectListComponent)
        }

        is ProjectsComponent.Config.Detail -> {
            TranslateContent(component = state.active.instance as TranslateComponent)
        }
    }
}
