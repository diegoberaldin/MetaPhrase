package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data

data class InvalidPlaceholderReference(
    val key: String,
    val extraPlaceholders: List<String>,
    val missingPlaceholders: List<String>,
)