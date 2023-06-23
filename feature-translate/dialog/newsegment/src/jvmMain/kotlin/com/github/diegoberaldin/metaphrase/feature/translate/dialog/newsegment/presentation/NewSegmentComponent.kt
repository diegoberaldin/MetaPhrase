package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * New segment component.
 */
interface NewSegmentComponent {

    /**
     * UI state
     */
    val uiState: StateFlow<NewSegmentUiState>

    /**
     * Events emitted after a successful [submit]
     */
    val done: SharedFlow<SegmentModel?>

    /**
     * Current project ID
     */
    var projectId: Int

    /**
     * Language for which the message should be added
     */
    var language: LanguageModel

    /**
     * Set the message key.
     *
     * @param value Value
     */
    fun setKey(value: String)

    /**
     * Set the message text.
     *
     * @param value Value
     */
    fun setText(value: String)

    /**
     * Close the dialog.
     */
    fun close()

    /**
     * Confirm creation of segment with current key and message.
     */
    fun submit()
}
