package projects.di

import org.koin.dsl.module
import projects.ui.DefaultProjectsComponent
import projects.ui.ProjectsComponent
import projectslist.di.projectListModule
import translate.di.translateModule

val projectsModule = module {
    includes(projectListModule)
    includes(translateModule)

    factory<ProjectsComponent> { params ->
        val defaultProjectsComponent = DefaultProjectsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            keyStore = get(),
            projectRepository = get(),
        )
        defaultProjectsComponent
    }
}
