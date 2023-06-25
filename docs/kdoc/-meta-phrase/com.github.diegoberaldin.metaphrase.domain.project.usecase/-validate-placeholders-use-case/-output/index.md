---
title: Output
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.usecase](../../index.html)/[ValidatePlaceholdersUseCase](../index.html)/[Output](index.html)



# Output

interface [Output](index.html)

Result of the validation.



#### Inheritors


| |
|---|
| [Valid](-valid/index.html) |
| [Invalid](-invalid/index.html) |


## Types


| Name | Summary |
|---|---|
| [Invalid](-invalid/index.html) | [jvm]<br>data class [Invalid](-invalid/index.html)(val keys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList()) : [ValidatePlaceholdersUseCase.Output](index.html)<br>Case where some errors were detected. |
| [Valid](-valid/index.html) | [jvm]<br>object [Valid](-valid/index.html) : [ValidatePlaceholdersUseCase.Output](index.html)<br>Case where no errors were detected. |

