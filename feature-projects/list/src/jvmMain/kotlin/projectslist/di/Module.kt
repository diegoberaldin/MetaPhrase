package projectslist.di

import org.koin.dsl.module
import projectslist.presentation.DefaultProjectListComponent
import projectslist.presentation.ProjectListComponent

val projectListModule = module {
    factory<ProjectListComponent> { params ->
        DefaultProjectListComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            projectRepository = get(),
            notificationCenter = get(),
        )
    }
}
