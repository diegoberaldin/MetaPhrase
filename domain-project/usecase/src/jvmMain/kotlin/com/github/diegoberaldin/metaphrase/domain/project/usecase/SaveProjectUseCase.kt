package com.github.diegoberaldin.metaphrase.domain.project.usecase

interface SaveProjectUseCase {
    suspend operator fun invoke(projectId: Int, path: String)
}
