package com.github.diegoberaldin.metaphrase.domain.project.data

/**
 * Segment model. A segment belongs to a specific language within a specific project.
 *
 * @property id segment ID
 * @property text segment text
 * @property key segment identifier (key)
 * @property translatable indicates whether this is a translatable or source-only segment
 * @constructor Create [SegmentModel]
 */
data class SegmentModel(
    val id: Int = 0,
    val text: String = "",
    val key: String = "",
    val translatable: Boolean = true,
)
