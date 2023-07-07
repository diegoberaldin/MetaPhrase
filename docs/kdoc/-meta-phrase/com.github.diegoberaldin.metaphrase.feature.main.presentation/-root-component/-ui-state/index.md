---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.presentation](../../index.html)/[RootComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val activeProject: [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, val isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isSaveEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

Root UI state.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(activeProject: [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isSaveEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [activeProject](active-project.html) | [jvm]<br>val [activeProject](active-project.html): [ProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null<br>project currently opened |
| [currentLanguage](current-language.html) | [jvm]<br>val [currentLanguage](current-language.html): [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>current language selected in the toolbar |
| [isEditing](is-editing.html) | [jvm]<br>val [isEditing](is-editing.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>flag indicating whether any message is being edited |
| [isLoading](is-loading.html) | [jvm]<br>val [isLoading](is-loading.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>flag indicating whether there is an operation running in the background |
| [isSaveEnabled](is-save-enabled.html) | [jvm]<br>val [isSaveEnabled](is-save-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>flag indicating whether the save menu action should be enabled |

