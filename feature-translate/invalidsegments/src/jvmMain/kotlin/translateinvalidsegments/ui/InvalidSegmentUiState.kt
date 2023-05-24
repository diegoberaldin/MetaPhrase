package translateinvalidsegments.ui

data class InvalidReference(
    val key: String,
    val extraPlaceholders: List<String>,
    val missingPlaceholders: List<String>,
)

data class InvalidSegmentUiState(
    val references: List<InvalidReference> = emptyList(),
    val currentIndex: Int? = null,
)
