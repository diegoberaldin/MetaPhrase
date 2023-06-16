package com.github.diegoberaldin.metaphrase.domain.project.data

data class TranslationUnit(
    val segment: SegmentModel,
    val original: SegmentModel? = null,
    val similarity: Int = 0,
    val origin: String = "",
)
