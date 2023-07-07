---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.presentation](../../index.html)/[TranslateComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val project: [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, val unitCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val needsSaving: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)

UI state for the translation editor.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(project: [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, unitCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, needsSaving: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [currentLanguage](current-language.html) | [jvm]<br>val [currentLanguage](current-language.html): [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>language selected in the translation toolbar |
| [isEditing](is-editing.html) | [jvm]<br>val [isEditing](is-editing.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>a flag indicating whether any message is opened for editing |
| [needsSaving](needs-saving.html) | [jvm]<br>val [needsSaving](needs-saving.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>whether the project needs saving |
| [project](project.html) | [jvm]<br>val [project](project.html): [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null<br>current project |
| [unitCount](unit-count.html) | [jvm]<br>val [unitCount](unit-count.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>number of translation units |

