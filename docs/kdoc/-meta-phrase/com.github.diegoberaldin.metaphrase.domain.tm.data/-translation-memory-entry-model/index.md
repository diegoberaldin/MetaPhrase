---
title: TranslationMemoryEntryModel
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.data](../index.html)/[TranslationMemoryEntryModel](index.html)



# TranslationMemoryEntryModel



[jvm]\
data class [TranslationMemoryEntryModel](index.html)(val id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val sourceText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val targetText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

Translation memory entry model.



## Constructors


| | |
|---|---|
| [TranslationMemoryEntryModel](-translation-memory-entry-model.html) | [jvm]<br>constructor(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, sourceText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, targetText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>Create [TranslationMemoryEntryModel](index.html) |


## Properties


| Name | Summary |
|---|---|
| [id](id.html) | [jvm]<br>val [id](id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>entry ID |
| [identifier](identifier.html) | [jvm]<br>val [identifier](identifier.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>message key or translation unit ID (tuid) |
| [origin](origin.html) | [jvm]<br>val [origin](origin.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>name of the source or project the message comes from |
| [sourceLang](source-lang.html) | [jvm]<br>val [sourceLang](source-lang.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>source language code |
| [sourceText](source-text.html) | [jvm]<br>val [sourceText](source-text.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>source text |
| [targetLang](target-lang.html) | [jvm]<br>val [targetLang](target-lang.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>target language code |
| [targetText](target-text.html) | [jvm]<br>val [targetText](target-text.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>target text |

