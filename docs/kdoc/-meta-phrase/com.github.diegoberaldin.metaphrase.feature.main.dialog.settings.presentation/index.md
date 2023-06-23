---
title: com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [SettingsComponent](-settings-component/index.html) | [jvm]<br>interface [SettingsComponent](-settings-component/index.html)<br>Settings component. |
| [SettingsLanguageUiState](-settings-language-ui-state/index.html) | [jvm]<br>data class [SettingsLanguageUiState](-settings-language-ui-state/index.html)(val availableLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val currentLanguage: [LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)<br>UI state for language data. |
| [SettingsMachineTranslationUiState](-settings-machine-translation-ui-state/index.html) | [jvm]<br>data class [SettingsMachineTranslationUiState](-settings-machine-translation-ui-state/index.html)(val availableProviders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MachineTranslationProvider](../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)&gt; = emptyList(), val currentProvider: [MachineTranslationProvider](../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)? = null, val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>UI state for machine transaltion |
| [SettingsUiState](-settings-ui-state/index.html) | [jvm]<br>data class [SettingsUiState](-settings-ui-state/index.html)(val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val similarityThreshold: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val spellcheckEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val appVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>UI state for general settings. |

