package com.github.diegoberaldin.metaphrase.domain.project.usecase

/**
 * Contract for the save project use case.
 */
interface SaveProjectUseCase {
    /**
     * Save the project under a given path as a TMX file.
     *
     * @param projectId ID of the project to save
     * @param path Path to write to
     */
    suspend operator fun invoke(projectId: Int, path: String)
}
