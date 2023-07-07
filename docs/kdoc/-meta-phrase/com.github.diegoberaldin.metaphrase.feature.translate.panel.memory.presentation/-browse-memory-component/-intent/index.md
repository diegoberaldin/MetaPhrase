---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation](../../index.html)/[BrowseMemoryComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [SetLanguages](-set-languages/index.html) |
| [SetSourceLanguage](-set-source-language/index.html) |
| [SetTargetLanguage](-set-target-language/index.html) |
| [SetSearch](-set-search/index.html) |
| [OnSearchFired](-on-search-fired/index.html) |
| [DeleteEntry](-delete-entry/index.html) |


## Types


| Name | Summary |
|---|---|
| [DeleteEntry](-delete-entry/index.html) | [jvm]<br>data class [DeleteEntry](-delete-entry/index.html)(val index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [BrowseMemoryComponent.Intent](index.html)<br>Delete an entry from the translation memory. |
| [OnSearchFired](-on-search-fired/index.html) | [jvm]<br>object [OnSearchFired](-on-search-fired/index.html) : [BrowseMemoryComponent.Intent](index.html)<br>Start a search. |
| [SetLanguages](-set-languages/index.html) | [jvm]<br>data class [SetLanguages](-set-languages/index.html)(val source: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val target: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null) : [BrowseMemoryComponent.Intent](index.html)<br>Set the language pair. |
| [SetSearch](-set-search/index.html) | [jvm]<br>data class [SetSearch](-set-search/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [BrowseMemoryComponent.Intent](index.html)<br>Set the search query. |
| [SetSourceLanguage](-set-source-language/index.html) | [jvm]<br>data class [SetSourceLanguage](-set-source-language/index.html)(val value: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?) : [BrowseMemoryComponent.Intent](index.html)<br>Set the source language. |
| [SetTargetLanguage](-set-target-language/index.html) | [jvm]<br>data class [SetTargetLanguage](-set-target-language/index.html)(val value: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?) : [BrowseMemoryComponent.Intent](index.html)<br>Set the target language. |

