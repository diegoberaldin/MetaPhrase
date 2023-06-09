package panelvalidate.ui

data class InvalidPlaceholderReference(
    val key: String,
    val extraPlaceholders: List<String>,
    val missingPlaceholders: List<String>,
)

data class InvalidSegmentUiState(
    val content: ValidationContent? = null,
)

sealed interface ValidationContent {
    data class InvalidPlaceholders(val references: List<InvalidPlaceholderReference> = emptyList()) : ValidationContent
}
