package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

/**
 * Contract for the clear glossary use case.
 */
interface ClearGlossaryUseCase {
    /**
     * Remove all terms from the glossary..
     */
    suspend operator fun invoke()
}
