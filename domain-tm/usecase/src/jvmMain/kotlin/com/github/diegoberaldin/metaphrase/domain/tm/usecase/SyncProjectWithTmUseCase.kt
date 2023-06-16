package com.github.diegoberaldin.metaphrase.domain.tm.usecase

interface SyncProjectWithTmUseCase {
    suspend operator fun invoke(projectId: Int)
}
