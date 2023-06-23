---
title: NewSegmentComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation](../index.html)/[NewSegmentComponent](index.html)



# NewSegmentComponent



[jvm]\
interface [NewSegmentComponent](index.html)



## Properties


| Name | Summary |
|---|---|
| [done](done.html) | [jvm]<br>abstract val [done](done.html): SharedFlow&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?&gt; |
| [language](language.html) | [jvm]<br>abstract var [language](language.html): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html) |
| [projectId](project-id.html) | [jvm]<br>abstract var [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[NewSegmentUiState](../-new-segment-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [close](close.html) | [jvm]<br>abstract fun [close](close.html)() |
| [setKey](set-key.html) | [jvm]<br>abstract fun [setKey](set-key.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setText](set-text.html) | [jvm]<br>abstract fun [setText](set-text.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [submit](submit.html) | [jvm]<br>abstract fun [submit](submit.html)() |

