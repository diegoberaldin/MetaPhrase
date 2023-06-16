package com.github.diegoberaldin.metaphrase.domain.project.data

data class SegmentModel(
    val id: Int = 0,
    val text: String = "",
    val key: String = "",
    val translatable: Boolean = true,
)
