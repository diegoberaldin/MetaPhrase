package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.MessageListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.TranslateToolbarComponent
import kotlinx.coroutines.flow.StateFlow

/**
 * Translate component.
 */
interface TranslateComponent {
    /**
     * Navigation slot for the toolbar
     */
    val toolbar: Value<ChildSlot<ToolbarConfig, TranslateToolbarComponent>>

    /**
     * Navigation slot for the message list
     */
    val messageList: Value<ChildSlot<MessageListConfig, MessageListComponent>>

    /**
     * Navigation slot for the dialogs
     */
    val dialog: Value<ChildSlot<DialogConfig, *>>

    /**
     * Navigation slot for the bottom panel
     */
    val panel: Value<ChildSlot<PanelConfig, *>>

    /**
     * UI state
     */
    val uiState: StateFlow<TranslateUiState>

    /**
     * Current project ID
     */
    var projectId: Int

    /**
     * A flag indicating whether any message is opened for editing
     */
    val isEditing: StateFlow<Boolean>

    /**
     * Language selected in the translation toolbar
     */
    val currentLanguage: StateFlow<LanguageModel?>

    /**
     * Save the current project to a TMX file.
     *
     * @param path file path
     */
    fun save(path: String)

    /**
     * Import a list of message for the current language from a resource file.
     *
     * @param path file path
     * @param type resource type
     */
    fun import(path: String, type: ResourceFileType)

    /**
     * Export the current language messages to a resource file.
     *
     * @param path file path
     * @param type resource type
     */
    fun export(path: String, type: ResourceFileType)

    /**
     * Moves the cursor to the previous message.
     */
    fun moveToPrevious()
    /**
     * Moves the cursor to the next message.
     */
    fun moveToNext()

    /**
     * End the current editing operation.
     */
    fun endEditing()

    /**
     * Copy the base (source) message to the translation field in the editor.
     */
    fun copyBase()

    /**
     * Add a new segment (opens dialog).
     */
    fun addSegment()

    /**
     * Delete the current segment.
     */
    fun deleteSegment()

    /**
     * Close any opened dialog.
     */
    fun closeDialog()

    /**
     * Toggle a bottom panel for a given section)
     *
     * @param config section configuration
     */
    fun togglePanel(config: PanelConfig)

    /**
     * Try and load similarities from the translation memory for the currently opened message.
     */
    fun tryLoadSimilarities()

    /**
     * Try and load terms from the glossary for the currently opened message.
     */
    fun tryLoadGlossary()

    /**
     * Loads the source version of the current message in machine translation component.
     * (This does not retrieve any result from the MT provider, use [machineTranslationRetrieve] after having called this method)
     */
    fun tryLoadMachineTranslation()

    /**
     * Export the global TM as a TMX file.
     *
     * @param path destination path
     */
    fun exportTmx(path: String)

    /**
     * Start a placeholder validation.
     */
    fun validatePlaceholders()

    /**
     * Insert the best match from translation memory in the translation field of the editor.
     */
    fun insertBestMatch()

    /**
     * Start a global spellcheck validation.
     */
    fun globalSpellcheck()

    /**
     * Export all the segments of the current project to the global translation memory.
     */
    fun syncWithTm()

    /**
     * Add a new glossary term.
     *
     * @param source source term
     * @param target target term
     */
    fun addGlossaryTerm(source: String?, target: String?)

    /**
     * Retrieve a suggestion for the current message from the machine translation provider.
     * In order for this to wark, the [tryLoadMachineTranslation] method should have been called earlier.
     */
    fun machineTranslationRetrieve()

    /**
     * Copy the MT suggestion into the translation editor.
     */
    fun machineTranslationInsert()

    /**
     * Copy the current translation of the editor into the MT suggestion.
     */
    fun machineTranslationCopyTarget()

    /**
     * Share the current suggestion (after editing) with the machine translation provider.
     */
    fun machineTranslationShare()

    /**
     * Share the whole project content (all messages for all languages) with the machine translation provider.
     */
    fun machineTranslationContributeTm()

    /**
     * Slot configuration for the translation toolbar.
     * This is the only config value available in the [toolbar] slot.
     */
    @Parcelize
    object ToolbarConfig : Parcelable

    /**
     * Slot configuration for the message list.
     * This is the only config value available in the [messageList] slot.
     */
    @Parcelize
    object MessageListConfig : Parcelable

    /**
     * Available dialog configurations for the [dialog] slot.
     */
    sealed interface DialogConfig : Parcelable {
        /**
         * No dialog (close any current dialog)
         */
        @Parcelize
        object None : DialogConfig

        /**
         * New segment dialog
         */
        @Parcelize
        object NewSegment : DialogConfig

        /**
         * New glossary term dialog
         *
         * @property target target term to pre-fill in the dialog
         * @constructor Create [NewGlossaryTerm]
         */
        @Parcelize
        data class NewGlossaryTerm(val target: String) : DialogConfig
    }

    /**
     * Available configurations for the [panel] slot.
     */
    sealed interface PanelConfig : Parcelable {
        /**
         * No panel section (close any opened section)
         */
        @Parcelize
        object None : PanelConfig

        /**
         * Translation memory matches section
         */
        @Parcelize
        object Matches : PanelConfig

        /**
         * Validation section (either placeholders or spellecheck)
         */
        @Parcelize
        object Validation : PanelConfig

        /**
         * TM content / concordancer section
         */
        @Parcelize
        object MemoryContent : PanelConfig

        /**
         * Glossary section
         */
        @Parcelize
        object Glossary : PanelConfig

        /**
         * Machine translation section
         */
        @Parcelize
        object MachineTranslation : PanelConfig
    }
}
