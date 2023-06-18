package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

internal class DefaultOpenProjectUseCase : OpenProjectUseCase {
    override suspend fun invoke(path: String): ProjectModel {
        TODO("Not yet implemented")
    }
}
