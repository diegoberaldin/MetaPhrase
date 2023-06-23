---
title: SettingsUiState
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation](../index.html)/[SettingsUiState](index.html)



# SettingsUiState



[jvm]\
data class [SettingsUiState](index.html)(val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val similarityThreshold: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val spellcheckEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val appVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

UI state for general settings.



## Constructors


| | |
|---|---|
| [SettingsUiState](-settings-ui-state.html) | [jvm]<br>constructor(isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, similarityThreshold: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, spellcheckEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, appVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>Create [SettingsUiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [appVersion](app-version.html) | [jvm]<br>val [appVersion](app-version.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>application version |
| [isLoading](is-loading.html) | [jvm]<br>val [isLoading](is-loading.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>true if there is a background operation in progress |
| [similarityThreshold](similarity-threshold.html) | [jvm]<br>val [similarityThreshold](similarity-threshold.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>current similarity threshold |
| [spellcheckEnabled](spellcheck-enabled.html) | [jvm]<br>val [spellcheckEnabled](spellcheck-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>true if spelling check is enabled |

