---
title: MemoryEntryDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao](../index.html)/[MemoryEntryDao](index.html)



# MemoryEntryDao

interface [MemoryEntryDao](index.html)

Memory entry Data Access Object.



#### Inheritors


| |
|---|
| [DefaultMemoryEntryDao](../-default-memory-entry-dao/index.html) |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new translation memory entry. |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Delete a translation memory entry. |
| [deleteAll](delete-all.html) | [jvm]<br>abstract suspend fun [deleteAll](delete-all.html)(origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Delete all the entries with a given origin. |
| [getByIdentifier](get-by-identifier.html) | [jvm]<br>abstract suspend fun [getByIdentifier](get-by-identifier.html)(identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?<br>Get a TM entry by identifier. |
| [getEntries](get-entries.html) | [jvm]<br>abstract suspend fun [getEntries](get-entries.html)(sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt;<br>abstract suspend fun [getEntries](get-entries.html)(sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt;<br>Get TM entries. |
| [getLanguageCodes](get-language-codes.html) | [jvm]<br>abstract suspend fun [getLanguageCodes](get-language-codes.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Get the list of distinct language codes in the TM. |
| [getTargetMessage](get-target-message.html) | [jvm]<br>abstract suspend fun [getTargetMessage](get-target-message.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?<br>Get the target message for a given TM entry key. |
| [update](update.html) | [jvm]<br>abstract suspend fun [update](update.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html))<br>Update a translation memory entry. |

