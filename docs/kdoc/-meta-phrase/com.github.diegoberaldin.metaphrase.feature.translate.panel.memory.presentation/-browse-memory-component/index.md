---
title: BrowseMemoryComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation](../index.html)/[BrowseMemoryComponent](index.html)



# BrowseMemoryComponent



[jvm]\
interface [BrowseMemoryComponent](index.html)

Browse memory component.



## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[BrowseMemoryUiState](../-browse-memory-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [deleteEntry](delete-entry.html) | [jvm]<br>abstract fun [deleteEntry](delete-entry.html)(index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Delete an entry from the translation memory. |
| [onSearchFired](on-search-fired.html) | [jvm]<br>abstract fun [onSearchFired](on-search-fired.html)()<br>Start a search. |
| [setLanguages](set-languages.html) | [jvm]<br>abstract fun [setLanguages](set-languages.html)(source: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, target: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)<br>Set the language pair. |
| [setSearch](set-search.html) | [jvm]<br>abstract fun [setSearch](set-search.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set the search query. |
| [setSourceLanguage](set-source-language.html) | [jvm]<br>abstract fun [setSourceLanguage](set-source-language.html)(value: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?)<br>Set the source language. |
| [setTargetLanguage](set-target-language.html) | [jvm]<br>abstract fun [setTargetLanguage](set-target-language.html)(value: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?)<br>Set the target language. |

