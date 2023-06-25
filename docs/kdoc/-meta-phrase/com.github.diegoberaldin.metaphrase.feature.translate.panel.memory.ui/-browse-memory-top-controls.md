---
title: BrowseMemoryTopControls
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.ui](index.html)/[BrowseMemoryTopControls](-browse-memory-top-controls.html)



# BrowseMemoryTopControls



[jvm]\




@Composable



fun [BrowseMemoryTopControls](-browse-memory-top-controls.html)(sourceLanguage: [LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, availableSourceLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), targetLanguage: [LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, availableTargetLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), currentSearch: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, onSourceLanguageSelected: ([LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onTargetLanguageSelected: ([LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onSearchChanged: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onSearchFired: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))



Top controls for the TM content panel.



#### Return



#### Parameters


jvm

| | |
|---|---|
| sourceLanguage | Source language |
| availableSourceLanguages | Available source languages |
| targetLanguage | Target language |
| availableTargetLanguages | Available target languages |
| currentSearch | Current search query |
| onSourceLanguageSelected | On source language selected callback |
| onTargetLanguageSelected | On target language selected callback |
| onSearchChanged | On search changed callback |
| onSearchFired | On search fired callback |




