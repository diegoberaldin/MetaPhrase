package data

data class TranslationUnit(
    val segment: SegmentModel,
    val original: SegmentModel? = null,
    val similarity: Int = 0,
)
