---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation](../../index.html)/[SettingsComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val availableLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val similarityThreshold: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val spellcheckEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val appVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val availableProviders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MachineTranslationProvider](../../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)&gt; = emptyList(), val currentProvider: [MachineTranslationProvider](../../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)? = null, val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

UI state for general settings.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(availableLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, similarityThreshold: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, spellcheckEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, appVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, availableProviders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MachineTranslationProvider](../../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)&gt; = emptyList(), currentProvider: [MachineTranslationProvider](../../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)? = null, key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>Create SettingsUiState |


## Properties


| Name | Summary |
|---|---|
| [appVersion](app-version.html) | [jvm]<br>val [appVersion](app-version.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>application version |
| [availableLanguages](available-languages.html) | [jvm]<br>val [availableLanguages](available-languages.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;<br>available app languages |
| [availableProviders](available-providers.html) | [jvm]<br>val [availableProviders](available-providers.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MachineTranslationProvider](../../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)&gt;<br>available Machine Translation providers |
| [currentLanguage](current-language.html) | [jvm]<br>val [currentLanguage](current-language.html): [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>current language |
| [currentProvider](current-provider.html) | [jvm]<br>val [currentProvider](current-provider.html): [MachineTranslationProvider](../../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)? = null<br>current MT provider |
| [isLoading](is-loading.html) | [jvm]<br>val [isLoading](is-loading.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>true if there is a background operation in progress |
| [key](key.html) | [jvm]<br>val [key](key.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>API key |
| [similarityThreshold](similarity-threshold.html) | [jvm]<br>val [similarityThreshold](similarity-threshold.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>current similarity threshold |
| [spellcheckEnabled](spellcheck-enabled.html) | [jvm]<br>val [spellcheckEnabled](spellcheck-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>true if spelling check is enabled |

