package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation

import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter

data class TranslateToolbarUiState(
    val currentLanguage: LanguageModel? = null,
    val currentTypeFilter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
    val availableFilters: List<TranslationUnitTypeFilter> = emptyList(),
    val availableLanguages: List<LanguageModel> = emptyList(),
    val currentSearch: String = "",
    val isEditing: Boolean = false,
)

internal fun TranslationUnitTypeFilter.toReadableString(): String = when (this) {
    TranslationUnitTypeFilter.ALL -> "unit_filter_all".localized()
    TranslationUnitTypeFilter.TRANSLATABLE -> "unit_filter_translatable".localized()
    TranslationUnitTypeFilter.UNTRANSLATED -> "unit_filter_untranslated".localized()
}
