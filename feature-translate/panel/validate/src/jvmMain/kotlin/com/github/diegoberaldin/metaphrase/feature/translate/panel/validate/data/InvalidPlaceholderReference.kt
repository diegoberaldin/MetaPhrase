package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data

/**
 * Invalid placeholder reference.
 *
 * @property key message key
 * @property extraPlaceholders placeholders in the target message that are not found in the source message
 * @property missingPlaceholders placeholders in the source message that are not found in the target message
 * @constructor Create [InvalidPlaceholderReference]
 */
data class InvalidPlaceholderReference(
    val key: String,
    val extraPlaceholders: List<String>,
    val missingPlaceholders: List<String>,
)
