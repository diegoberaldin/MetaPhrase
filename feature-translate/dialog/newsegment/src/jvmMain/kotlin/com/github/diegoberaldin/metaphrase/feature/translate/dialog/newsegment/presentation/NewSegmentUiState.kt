package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation

/**
 * New segment dialog UI state.
 *
 * @property key message key
 * @property keyError error for the key field
 * @property text message text
 * @property textError error for the text field
 * @property isLoading boolean indicating whether a background operation is in progress
 * @constructor Create [NewSegmentUiState]
 */
data class NewSegmentUiState(
    val key: String = "",
    val keyError: String = "",
    val text: String = "",
    val textError: String = "",
    val isLoading: Boolean = false,
)
