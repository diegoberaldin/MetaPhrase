package data

import data.SegmentModel

data class TranslationUnit(
    val segment: SegmentModel,
    val original: SegmentModel? = null,
)