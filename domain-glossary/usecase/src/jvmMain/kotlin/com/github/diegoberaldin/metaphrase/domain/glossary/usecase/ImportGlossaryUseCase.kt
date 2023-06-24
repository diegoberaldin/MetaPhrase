package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

/**
 * Contract for the import glossary use case.
 */
interface ImportGlossaryUseCase {
    /**
     * Import the terms contained in the CSV file at a given path into the global glossary.
     *
     * @param path Source file path
     */
    suspend operator fun invoke(path: String)
}
