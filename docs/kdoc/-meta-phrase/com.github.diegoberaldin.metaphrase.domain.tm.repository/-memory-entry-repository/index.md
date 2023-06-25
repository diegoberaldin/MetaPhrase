---
title: MemoryEntryRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.repository](../index.html)/[MemoryEntryRepository](index.html)



# MemoryEntryRepository



[jvm]\
interface [MemoryEntryRepository](index.html)

Contrast of the memory entry repository.



## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new translation memory. |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Delete a translation memory entry. |
| [deleteAll](delete-all.html) | [jvm]<br>abstract suspend fun [deleteAll](delete-all.html)(origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)<br>Delete all the translation memory entries having a given origin. |
| [getByIdentifier](get-by-identifier.html) | [jvm]<br>abstract suspend fun [getByIdentifier](get-by-identifier.html)(identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?<br>Get translation memory entries by identifier. |
| [getEntries](get-entries.html) | [jvm]<br>abstract suspend fun [getEntries](get-entries.html)(sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt;<br>Get translation memory entries.<br>[jvm]<br>abstract suspend fun [getEntries](get-entries.html)(sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt;<br>Get TM entries. |
| [getLanguageCodes](get-language-codes.html) | [jvm]<br>abstract suspend fun [getLanguageCodes](get-language-codes.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Get the list of distinct language codes that are found in the TM. |
| [getTranslation](get-translation.html) | [jvm]<br>abstract suspend fun [getTranslation](get-translation.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?<br>Get an entry with a given target message for a source message. |
| [update](update.html) | [jvm]<br>abstract suspend fun [update](update.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html))<br>Update a translation memory entry. |

