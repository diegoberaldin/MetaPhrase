package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation

import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter

/**
 * UI state for the translation toolbar
 *
 * @property currentLanguage currently selected language
 * @property currentTypeFilter currently selected message filter
 * @property availableFilters available message filters
 * @property availableLanguages available languages
 * @property currentSearch currently selected search query
 * @property isEditing a boolean indicating whether a message is being edited
 * @constructor Create [TranslateToolbarUiState]
 */
data class TranslateToolbarUiState(
    val currentLanguage: LanguageModel? = null,
    val currentTypeFilter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
    val availableFilters: List<TranslationUnitTypeFilter> = emptyList(),
    val availableLanguages: List<LanguageModel> = emptyList(),
    val currentSearch: String = "",
    val isEditing: Boolean = false,
)

/**
 * Get a readable description of a message filter.
 *
 * @receiver [TranslationUnitTypeFilter]
 * @return user-friendly representation of the filter
 */
internal fun TranslationUnitTypeFilter.toReadableString(): String = when (this) {
    TranslationUnitTypeFilter.ALL -> "unit_filter_all".localized()
    TranslationUnitTypeFilter.TRANSLATABLE -> "unit_filter_translatable".localized()
    TranslationUnitTypeFilter.UNTRANSLATED -> "unit_filter_untranslated".localized()
}
