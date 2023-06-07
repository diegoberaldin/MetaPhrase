package translationmemory.similarity

internal interface SimilarityCalculator {
    operator fun invoke(segment1: String, segment2: String): Float
}
