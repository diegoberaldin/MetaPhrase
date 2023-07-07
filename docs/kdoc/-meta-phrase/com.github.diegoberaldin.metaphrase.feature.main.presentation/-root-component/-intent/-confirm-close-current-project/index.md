---
title: ConfirmCloseCurrentProject
---
//[MetaPhrase](../../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.presentation](../../../index.html)/[RootComponent](../../index.html)/[Intent](../index.html)/[ConfirmCloseCurrentProject](index.html)



# ConfirmCloseCurrentProject

data class [ConfirmCloseCurrentProject](index.html)(val openAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val newAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [RootComponent.Intent](../index.html)

Sends user confirmation to close the current project with an optional action afterwards.



#### Parameters


jvm

| | |
|---|---|
| openAfter | if set to true, after closing the &quot;Open project&quot; dialog will be opened |
| newAfter | if set to true, after closing the &quot;New project&quot; dialog will be opened |



## Constructors


| | |
|---|---|
| [ConfirmCloseCurrentProject](-confirm-close-current-project.html) | [jvm]<br>constructor(openAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, newAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |


## Properties


| Name | Summary |
|---|---|
| [newAfter](new-after.html) | [jvm]<br>val [newAfter](new-after.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [openAfter](open-after.html) | [jvm]<br>val [openAfter](open-after.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |

