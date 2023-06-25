---
title: NewSegmentComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation](../index.html)/[NewSegmentComponent](index.html)



# NewSegmentComponent



[jvm]\
interface [NewSegmentComponent](index.html)

New segment component.



## Properties


| Name | Summary |
|---|---|
| [done](done.html) | [jvm]<br>abstract val [done](done.html): SharedFlow&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?&gt;<br>Events emitted after a successful [submit](submit.html) |
| [language](language.html) | [jvm]<br>abstract var [language](language.html): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)<br>Language for which the message should be added |
| [projectId](project-id.html) | [jvm]<br>abstract var [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Current project ID |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[NewSegmentUiState](../-new-segment-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [close](close.html) | [jvm]<br>abstract fun [close](close.html)()<br>Close the dialog. |
| [setKey](set-key.html) | [jvm]<br>abstract fun [setKey](set-key.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set the message key. |
| [setText](set-text.html) | [jvm]<br>abstract fun [setText](set-text.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set the message text. |
| [submit](submit.html) | [jvm]<br>abstract fun [submit](submit.html)()<br>Confirm creation of segment with current key and message. |

