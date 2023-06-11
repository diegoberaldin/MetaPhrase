package panelvalidate.data

data class InvalidPlaceholderReference(
    val key: String,
    val extraPlaceholders: List<String>,
    val missingPlaceholders: List<String>,
)