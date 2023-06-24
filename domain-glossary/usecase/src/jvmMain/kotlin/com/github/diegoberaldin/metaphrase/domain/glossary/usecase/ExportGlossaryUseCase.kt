package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

/**
 * Contract for the export glossary use case.
 */
interface ExportGlossaryUseCase {
    /**
     * Export the global glossary content as a CSV file at a given path..
     *
     * @param path Destination file path
     */
    suspend operator fun invoke(path: String)
}
