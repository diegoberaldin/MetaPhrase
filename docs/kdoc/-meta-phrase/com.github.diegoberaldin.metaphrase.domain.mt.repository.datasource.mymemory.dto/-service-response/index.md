---
title: ServiceResponse
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.dto](../index.html)/[ServiceResponse](index.html)



# ServiceResponse



[jvm]\
@Serializable



data class [ServiceResponse](index.html)(val matches: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ServiceMatch](../-service-match/index.html)&gt; = emptyList(), val quotaFinished: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val responseStatus: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)



## Constructors


| | |
|---|---|
| [ServiceResponse](-service-response.html) | [jvm]<br>constructor(matches: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ServiceMatch](../-service-match/index.html)&gt; = emptyList(), quotaFinished: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, responseStatus: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) |


## Properties


| Name | Summary |
|---|---|
| [matches](matches.html) | [jvm]<br>val [matches](matches.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ServiceMatch](../-service-match/index.html)&gt; |
| [quotaFinished](quota-finished.html) | [jvm]<br>val [quotaFinished](quota-finished.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [responseStatus](response-status.html) | [jvm]<br>val [responseStatus](response-status.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

