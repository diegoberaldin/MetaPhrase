---
title: DefaultMemoryEntryDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao](../index.html)/[DefaultMemoryEntryDao](index.html)



# DefaultMemoryEntryDao



[jvm]\
class [DefaultMemoryEntryDao](index.html) : [MemoryEntryDao](../-memory-entry-dao/index.html)



## Constructors


| | |
|---|---|
| [DefaultMemoryEntryDao](-default-memory-entry-dao.html) | [jvm]<br>constructor() |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>open suspend override fun [create](create.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new translation memory entry. |
| [delete](delete.html) | [jvm]<br>open suspend override fun [delete](delete.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Delete a translation memory entry. |
| [deleteAll](delete-all.html) | [jvm]<br>open suspend override fun [deleteAll](delete-all.html)(origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Delete all the entries with a given origin. |
| [getByIdentifier](get-by-identifier.html) | [jvm]<br>open suspend override fun [getByIdentifier](get-by-identifier.html)(identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?<br>Get a TM entry by identifier. |
| [getEntries](get-entries.html) | [jvm]<br>open suspend override fun [getEntries](get-entries.html)(sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt;<br>open suspend override fun [getEntries](get-entries.html)(sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt;<br>Get TM entries. |
| [getLanguageCodes](get-language-codes.html) | [jvm]<br>open suspend override fun [getLanguageCodes](get-language-codes.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Get the list of distinct language codes in the TM. |
| [getTargetMessage](get-target-message.html) | [jvm]<br>open suspend override fun [getTargetMessage](get-target-message.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?<br>Get the target message for a given TM entry key. |
| [update](update.html) | [jvm]<br>open suspend override fun [update](update.html)(model: [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html))<br>Update a translation memory entry. |

