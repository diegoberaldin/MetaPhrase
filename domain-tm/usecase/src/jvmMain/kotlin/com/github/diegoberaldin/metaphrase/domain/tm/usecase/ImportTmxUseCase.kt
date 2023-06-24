package com.github.diegoberaldin.metaphrase.domain.tm.usecase

/**
 * Contract for the import TMX use case.
 */
interface ImportTmxUseCase {
    /**
     * Import the content of a TMX file into the global translation memory.
     *
     * @param path file path
     */
    suspend operator fun invoke(path: String)
}

