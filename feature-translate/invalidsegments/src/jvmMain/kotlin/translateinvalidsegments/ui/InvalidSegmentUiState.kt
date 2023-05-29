package translateinvalidsegments.ui

data class InvalidReference(
    val key: String,
    val extraPlaceholders: List<String>,
    val missingPlaceholders: List<String>,
)

enum class InvalidSegmentStage {
    INITIAL,
    CONTENT,
}

data class InvalidSegmentUiState(
    val stage: InvalidSegmentStage = InvalidSegmentStage.INITIAL,
    val references: List<InvalidReference> = emptyList(),
    val currentIndex: Int? = null,
)
