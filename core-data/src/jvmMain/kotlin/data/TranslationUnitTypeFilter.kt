package data

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