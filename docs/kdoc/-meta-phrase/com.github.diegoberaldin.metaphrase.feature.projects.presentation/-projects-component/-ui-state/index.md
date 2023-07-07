---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.presentation](../../index.html)/[ProjectsComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val activeProject: [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, val isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)

UI state.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(activeProject: [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [activeProject](active-project.html) | [jvm]<br>val [activeProject](active-project.html): [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null<br>Currently active (opened) project. |
| [currentLanguage](current-language.html) | [jvm]<br>val [currentLanguage](current-language.html): [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>Currently selected language in the translation toolbar (if any). |
| [isEditing](is-editing.html) | [jvm]<br>val [isEditing](is-editing.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>True if a message (Translation Unit) is being edited. |

