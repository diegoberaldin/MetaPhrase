package com.github.diegoberaldin.metaphrase.domain.tm.usecase

/**
 * Contract for the sync project with TM use case.
 */
interface SyncProjectWithTmUseCase {
    /**
     * Import all the units of a project into the global TM.
     *
     * @param projectId Project ID
     */
    suspend operator fun invoke(projectId: Int)
}
