package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.MessageListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.TranslateToolbarComponent

/**
 * Translate component.
 */
interface TranslateComponent :
    MviModel<TranslateComponent.ViewIntent, TranslateComponent.UiState, TranslateComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface ViewIntent {
        /**
         * Save the current project to a TMX file.
         *
         * @param path file path
         */
        data class Save(val path: String) : ViewIntent

        /**
         * Import a list of message for the current language from a resource file.
         *
         * @param path file path
         * @param type resource type
         */
        data class Import(val path: String, val type: ResourceFileType) : ViewIntent

        /**
         * Export the current language messages to a resource file.
         *
         * @param path file path
         * @param type resource type
         */
        data class Export(val path: String, val type: ResourceFileType) : ViewIntent

        /**
         * Moves the cursor to the previous message.
         */
        object MoveToPrevious : ViewIntent

        /**
         * Moves the cursor to the next message.
         */
        object MoveToNext : ViewIntent

        /**
         * End the current editing operation.
         */
        object EndEditing : ViewIntent

        /**
         * Copy the base (source) message to the translation field in the editor.
         */
        object CopyBase : ViewIntent

        /**
         * Add a new segment (opens dialog).
         */
        object AddSegment : ViewIntent

        /**
         * Delete the current segment.
         */
        object DeleteSegment : ViewIntent

        /**
         * Close any opened dialog.
         */
        object CloseDialog : ViewIntent

        /**
         * Toggle a bottom panel for a given section)
         *
         * @param config section configuration
         */
        data class TogglePanel(val config: PanelConfig) : ViewIntent

        /**
         * Try and load similarities from the translation memory for the currently opened message.
         */
        object TryLoadSimilarities : ViewIntent

        /**
         * Try and load terms from the glossary for the currently opened message.
         */
        object TryLoadGlossary : ViewIntent

        /**
         * Loads the source version of the current message in machine translation component.
         * (This does not retrieve any result from the MT provider, use [MachineTranslationRetrieve] after having called this method)
         */
        object TryLoadMachineTranslation : ViewIntent

        /**
         * Export the global TM as a TMX file.
         *
         * @param path destination path
         */
        data class ExportTmx(val path: String) : ViewIntent

        /**
         * Start a placeholder validation.
         */
        object ValidatePlaceholders : ViewIntent

        /**
         * Insert the best match from translation memory in the translation field of the editor.
         */
        object InsertBestMatch : ViewIntent

        /**
         * Start a global spellcheck validation.
         */
        object GlobalSpellcheck : ViewIntent

        /**
         * Export all the segments of the current project to the global translation memory.
         */
        object SyncWithTm : ViewIntent

        /**
         * Add a new glossary term.
         *
         * @param source source term
         * @param target target term
         */
        data class AddGlossaryTerm(val source: String?, val target: String?) : ViewIntent

        /**
         * Retrieve a suggestion for the current message from the machine translation provider.
         * In order for this to work, the [TryLoadMachineTranslation] intent should have been sent earlier.
         */
        object MachineTranslationRetrieve : ViewIntent

        /**
         * Copy the MT suggestion into the translation editor.
         */
        object MachineTranslationInsert : ViewIntent

        /**
         * Copy the current translation of the editor into the MT suggestion.
         */
        object MachineTranslationCopyTarget : ViewIntent

        /**
         * Share the current suggestion (after editing) with the machine translation provider.
         */
        object MachineTranslationShare : ViewIntent

        /**
         * Share the whole project content (all messages for all languages) with the machine translation provider.
         */
        object MachineTranslationContributeTm : ViewIntent
    }

    /**
     * UI state for the translation editor.
     *
     * @property project current project
     * @property unitCount number of translation units
     * @property needsSaving whether the project needs saving
     * @property isEditing a flag indicating whether any message is opened for editing
     * @property currentLanguage language selected in the translation toolbar
     * @constructor Create [UiState]
     */
    data class UiState(
        val project: ProjectModel? = null,
        val unitCount: Int = 0,
        val needsSaving: Boolean = false,
        val isEditing: Boolean = false,
        val currentLanguage: LanguageModel? = null,
    )

    /**
     * Effects.
     */
    sealed interface Effect

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
     * Current project ID
     */
    var projectId: Int

    /**data class   * Slot configuration for the translation toolbar. : _root_ide_package_.com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent.ViewIntent
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
