package projects.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import projectslist.ProjectListComponent
import projectslist.ProjectsListContent
import translate.ui.TranslateComponent
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
