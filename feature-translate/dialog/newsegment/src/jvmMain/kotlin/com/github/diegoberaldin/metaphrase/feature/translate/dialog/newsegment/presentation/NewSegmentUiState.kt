package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation

data class NewSegmentUiState(
    val key: String = "",
    val keyError: String = "",
    val text: String = "",
    val textError: String = "",
    val isLoading: Boolean = false,
)
