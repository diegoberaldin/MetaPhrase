---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation](../../index.html)/[MachineTranslationComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val translation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val updateTextSwitch: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

Machine translation panel UI state.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, translation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, updateTextSwitch: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [isLoading](is-loading.html) | [jvm]<br>val [isLoading](is-loading.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>indication whether there is a background operation in progress |
| [translation](translation.html) | [jvm]<br>val [translation](translation.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>suggestion from MT |
| [updateTextSwitch](update-text-switch.html) | [jvm]<br>val [updateTextSwitch](update-text-switch.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>flag to trigger suggestion updates programmatically |

