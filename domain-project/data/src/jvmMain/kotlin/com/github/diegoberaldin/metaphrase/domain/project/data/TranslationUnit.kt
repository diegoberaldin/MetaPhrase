package com.github.diegoberaldin.metaphrase.domain.project.data

/**
 * A pair of a source segment and a target segment with the same key is a translation unit.
 * If this is a TM match, it has a similarity rate to another translation unit, based on the distance
 * between the respective source segments.
 *
 * @property segment target segment
 * @property original source segment
 * @property similarity similarity rate
 * @property origin origin if this is a match coming from the translation memory
 * @constructor Create [TranslationUnit]
 */
data class TranslationUnit(
    val segment: SegmentModel,
    val original: SegmentModel? = null,
    val similarity: Int = 0,
    val origin: String = "",
)
