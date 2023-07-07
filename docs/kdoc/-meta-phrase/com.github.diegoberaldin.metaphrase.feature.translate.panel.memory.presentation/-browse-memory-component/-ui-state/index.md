---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation](../../index.html)/[BrowseMemoryComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val sourceLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val availableSourceLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val targetLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val availableTargetLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val currentSearch: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val entries: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt; = emptyList())

UI state from the TM content panel.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(sourceLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, availableSourceLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), targetLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, availableTargetLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), currentSearch: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, entries: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt; = emptyList())<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [availableSourceLanguages](available-source-languages.html) | [jvm]<br>val [availableSourceLanguages](available-source-languages.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;<br>available source languages |
| [availableTargetLanguages](available-target-languages.html) | [jvm]<br>val [availableTargetLanguages](available-target-languages.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;<br>available target languages |
| [currentSearch](current-search.html) | [jvm]<br>val [currentSearch](current-search.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>search string |
| [entries](entries.html) | [jvm]<br>val [entries](entries.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt;<br>memory entries to display |
| [sourceLanguage](source-language.html) | [jvm]<br>val [sourceLanguage](source-language.html): [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>source language |
| [targetLanguage](target-language.html) | [jvm]<br>val [targetLanguage](target-language.html): [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>target language |

