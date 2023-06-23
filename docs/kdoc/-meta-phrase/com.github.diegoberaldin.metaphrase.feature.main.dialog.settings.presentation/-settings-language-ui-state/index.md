---
title: SettingsLanguageUiState
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation](../index.html)/[SettingsLanguageUiState](index.html)



# SettingsLanguageUiState



[jvm]\
data class [SettingsLanguageUiState](index.html)(val availableLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val currentLanguage: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)

UI state for language data.



## Constructors


| | |
|---|---|
| [SettingsLanguageUiState](-settings-language-ui-state.html) | [jvm]<br>constructor(availableLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), currentLanguage: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)<br>Create [SettingsLanguageUiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [availableLanguages](available-languages.html) | [jvm]<br>val [availableLanguages](available-languages.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;<br>available app languages |
| [currentLanguage](current-language.html) | [jvm]<br>val [currentLanguage](current-language.html): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>current language |

