package com.github.diegoberaldin.metaphrase.domain.tm.usecase.similarity

/**
 * Contract for the logic that calculates the similarity between two messages.
 */
internal interface SimilarityCalculator {
    /**
     * Calculate similarity between two messages.
     *
     * @param segment1 first segment
     * @param segment2 first segment
     * @return similarity threshold
     */
    operator fun invoke(segment1: String, segment2: String): Float
}
