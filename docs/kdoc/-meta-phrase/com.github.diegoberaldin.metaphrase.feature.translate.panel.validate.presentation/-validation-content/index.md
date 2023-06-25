---
title: ValidationContent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation](../index.html)/[ValidationContent](index.html)



# ValidationContent

interface [ValidationContent](index.html)

Available validation panel content.



#### Inheritors


| |
|---|
| [InvalidPlaceholders](-invalid-placeholders/index.html) |
| [SpellingMistakes](-spelling-mistakes/index.html) |


## Types


| Name | Summary |
|---|---|
| [InvalidPlaceholders](-invalid-placeholders/index.html) | [jvm]<br>data class [InvalidPlaceholders](-invalid-placeholders/index.html)(val references: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InvalidPlaceholderReference](../../com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data/-invalid-placeholder-reference/index.html)&gt; = emptyList()) : [ValidationContent](index.html)<br>Placeholders validation content. |
| [SpellingMistakes](-spelling-mistakes/index.html) | [jvm]<br>data class [SpellingMistakes](-spelling-mistakes/index.html)(val references: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellingMistakeReference](../../com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data/-spelling-mistake-reference/index.html)&gt; = emptyList()) : [ValidationContent](index.html)<br>Spelling mistakes content. |

