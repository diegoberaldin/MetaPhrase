package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

interface OpenProjectUseCase {

    suspend operator fun invoke(path: String): ProjectModel?
}
