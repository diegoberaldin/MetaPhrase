package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

/**
 * Contract for the open project use case.
 */
interface OpenProjectUseCase {

    /**
     * Open a project saved on a given path as a TMX file. Opening implies that all the languages and segments of
     * that specific project will be loaded in the application DB for subsequent queries.
     *
     * @param path Path of the TMX file
     * @return [ProjectModel] or null if there was an error
     */
    suspend operator fun invoke(path: String): ProjectModel?
}
