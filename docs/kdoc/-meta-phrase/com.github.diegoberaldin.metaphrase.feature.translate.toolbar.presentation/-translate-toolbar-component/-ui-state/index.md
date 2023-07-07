---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation](../../index.html)/[TranslateToolbarComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val currentTypeFilter: [TranslationUnitTypeFilter](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html) = TranslationUnitTypeFilter.ALL, val availableFilters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnitTypeFilter](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html)&gt; = emptyList(), val availableLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val currentSearch: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

UI state for the translation toolbar



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, currentTypeFilter: [TranslationUnitTypeFilter](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html) = TranslationUnitTypeFilter.ALL, availableFilters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnitTypeFilter](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html)&gt; = emptyList(), availableLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), currentSearch: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [availableFilters](available-filters.html) | [jvm]<br>val [availableFilters](available-filters.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnitTypeFilter](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html)&gt;<br>available message filters |
| [availableLanguages](available-languages.html) | [jvm]<br>val [availableLanguages](available-languages.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;<br>available languages |
| [currentLanguage](current-language.html) | [jvm]<br>val [currentLanguage](current-language.html): [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>currently selected language |
| [currentSearch](current-search.html) | [jvm]<br>val [currentSearch](current-search.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>currently selected search query |
| [currentTypeFilter](current-type-filter.html) | [jvm]<br>val [currentTypeFilter](current-type-filter.html): [TranslationUnitTypeFilter](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html)<br>currently selected message filter |
| [isEditing](is-editing.html) | [jvm]<br>val [isEditing](is-editing.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>a boolean indicating whether a message is being edited |

