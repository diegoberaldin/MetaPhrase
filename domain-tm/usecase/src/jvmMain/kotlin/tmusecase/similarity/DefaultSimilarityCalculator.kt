package tmusecase.similarity

import kotlin.math.max

internal class DefaultSimilarityCalculator : SimilarityCalculator {
    override operator fun invoke(segment1: String, segment2: String): Float {
        val distance = levenshteinDistance(source = segment1, target = segment2)
        return 1f - distance.toFloat() / max(segment1.length, segment2.length)
    }

    private fun levenshteinDistance(source: String, target: String): Int {
        if (source == target) return 0
        if (source == "") return target.length
        if (target == "") return source.length

        val previous = IntArray(target.length + 1) { it }
        val current = IntArray(target.length + 1)

        for (i in source.indices) {
            current[0] = i + 1
            for (j in target.indices) {
                val cost = if (source[i] == target[j]) 0 else 1
                current[j + 1] = (current[j] + 1).coerceAtMost((previous[j + 1] + 1).coerceAtMost(previous[j] + cost))
            }
            for (j in 0..target.length) previous[j] = current[j]
        }
        return current[target.length]
    }
}
