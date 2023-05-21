package translate.ui.toolbar

import data.LanguageModel
import localized

enum class TranslationUnitTypeFilter {
    ALL,
    UNTRANSLATED,
    TRANSLATABLE,
}

internal fun TranslationUnitTypeFilter.toReadableString(): String = when (this) {
    TranslationUnitTypeFilter.ALL -> "unit_filter_all".localized()
    TranslationUnitTypeFilter.TRANSLATABLE -> "unit_filter_translatable".localized()
    TranslationUnitTypeFilter.UNTRANSLATED -> "unit_filter_untranslated".localized()
}

data class TranslateToolbarUiState(
    val currentLanguage: LanguageModel? = null,
    val currentTypeFilter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
    val availableFilters: List<TranslationUnitTypeFilter> = emptyList(),
    val availableLanguages: List<LanguageModel> = emptyList(),
    val currentSearch: String = "",
    val isEditing: Boolean = false,
)
