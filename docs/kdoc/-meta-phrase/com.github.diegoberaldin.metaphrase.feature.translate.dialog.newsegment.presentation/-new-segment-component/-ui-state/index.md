---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation](../../index.html)/[NewSegmentComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val keyError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val textError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

New segment dialog UI state.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, keyError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, textError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [isLoading](is-loading.html) | [jvm]<br>val [isLoading](is-loading.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>boolean indicating whether a background operation is in progress |
| [key](key.html) | [jvm]<br>val [key](key.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>message key |
| [keyError](key-error.html) | [jvm]<br>val [keyError](key-error.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>error for the key field |
| [text](text.html) | [jvm]<br>val [text](text.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>message text |
| [textError](text-error.html) | [jvm]<br>val [textError](text-error.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>error for the text field |

