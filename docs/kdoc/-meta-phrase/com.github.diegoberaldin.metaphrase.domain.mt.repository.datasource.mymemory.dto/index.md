---
title: com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.dto
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.dto](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [ServiceKey](-service-key/index.html) | [jvm]<br>@Serializable<br>data class [ServiceKey](-service-key/index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) |
| [ServiceMatch](-service-match/index.html) | [jvm]<br>@Serializable<br>data class [ServiceMatch](-service-match/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val segment: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val translation: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [ServiceResponse](-service-response/index.html) | [jvm]<br>@Serializable<br>data class [ServiceResponse](-service-response/index.html)(val matches: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ServiceMatch](-service-match/index.html)&gt; = emptyList(), val quotaFinished: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val responseStatus: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) |

