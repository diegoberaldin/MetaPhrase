package projects.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import projects.presentation.ProjectsComponent
import projectslist.presentation.ProjectListComponent
import projectslist.ui.ProjectsListContent
import translate.presentation.TranslateComponent
import translate.ui.TranslateContent

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
