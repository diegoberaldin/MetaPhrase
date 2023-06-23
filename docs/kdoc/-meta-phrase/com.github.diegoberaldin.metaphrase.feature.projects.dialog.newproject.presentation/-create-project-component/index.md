---
title: CreateProjectComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation](../index.html)/[CreateProjectComponent](index.html)



# CreateProjectComponent



[jvm]\
interface [CreateProjectComponent](index.html)



## Properties


| Name | Summary |
|---|---|
| [done](done.html) | [jvm]<br>abstract val [done](done.html): SharedFlow&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?&gt; |
| [languagesUiState](languages-ui-state.html) | [jvm]<br>abstract val [languagesUiState](languages-ui-state.html): StateFlow&lt;[CreateProjectLanguagesUiState](../-create-project-languages-ui-state/index.html)&gt; |
| [projectId](project-id.html) | [jvm]<br>abstract var [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[CreateProjectUiState](../-create-project-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addLanguage](add-language.html) | [jvm]<br>abstract fun [addLanguage](add-language.html)(value: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) |
| [removeLanguage](remove-language.html) | [jvm]<br>abstract fun [removeLanguage](remove-language.html)(value: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) |
| [setBaseLanguage](set-base-language.html) | [jvm]<br>abstract fun [setBaseLanguage](set-base-language.html)(value: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) |
| [setName](set-name.html) | [jvm]<br>abstract fun [setName](set-name.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [submit](submit.html) | [jvm]<br>abstract fun [submit](submit.html)() |

