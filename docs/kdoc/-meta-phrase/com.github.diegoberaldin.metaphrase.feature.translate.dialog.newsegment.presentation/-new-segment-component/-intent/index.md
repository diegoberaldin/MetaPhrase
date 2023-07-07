---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation](../../index.html)/[NewSegmentComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [SetKey](-set-key/index.html) |
| [SetText](-set-text/index.html) |
| [Close](-close/index.html) |
| [Submit](-submit/index.html) |


## Types


| Name | Summary |
|---|---|
| [Close](-close/index.html) | [jvm]<br>object [Close](-close/index.html) : [NewSegmentComponent.Intent](index.html)<br>Close the dialog. |
| [SetKey](-set-key/index.html) | [jvm]<br>data class [SetKey](-set-key/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [NewSegmentComponent.Intent](index.html)<br>Set the message key. |
| [SetText](-set-text/index.html) | [jvm]<br>data class [SetText](-set-text/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [NewSegmentComponent.Intent](index.html)<br>Set the message text. |
| [Submit](-submit/index.html) | [jvm]<br>object [Submit](-submit/index.html) : [NewSegmentComponent.Intent](index.html)<br>Confirm creation of segment with current key and message. |

