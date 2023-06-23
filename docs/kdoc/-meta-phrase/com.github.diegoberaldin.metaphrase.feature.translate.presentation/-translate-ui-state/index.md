---
title: TranslateUiState
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.presentation](../index.html)/[TranslateUiState](index.html)



# TranslateUiState



[jvm]\
data class [TranslateUiState](index.html)(val project: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, val unitCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val needsSaving: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

UI state for the translation editor.



## Constructors


| | |
|---|---|
| [TranslateUiState](-translate-ui-state.html) | [jvm]<br>constructor(project: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, unitCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, needsSaving: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>Create [TranslateUiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [needsSaving](needs-saving.html) | [jvm]<br>val [needsSaving](needs-saving.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>whether the project needs saving |
| [project](project.html) | [jvm]<br>val [project](project.html): [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null<br>current project |
| [unitCount](unit-count.html) | [jvm]<br>val [unitCount](unit-count.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>number of translation units |

