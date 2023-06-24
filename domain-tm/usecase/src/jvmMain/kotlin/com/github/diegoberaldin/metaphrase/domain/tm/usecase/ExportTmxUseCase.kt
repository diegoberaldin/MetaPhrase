package com.github.diegoberaldin.metaphrase.domain.tm.usecase

/**
 * Contract for the export TMX use case.
 */
interface ExportTmxUseCase {
    /**
     * Export the TM content into a TMX file.
     *
     * @param sourceLang source lang (administration) code
     * @param path file path
     */
    suspend operator fun invoke(sourceLang: String, path: String)
}

