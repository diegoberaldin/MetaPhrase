package com.github.diegoberaldin.metaphrase.domain.tm.usecase

/**
 * Contract for clear translation memory use case.
 */
interface ClearTmUseCase {
    /**
     * Clear the content of the translation memory.
     */
    suspend operator fun invoke()
}

