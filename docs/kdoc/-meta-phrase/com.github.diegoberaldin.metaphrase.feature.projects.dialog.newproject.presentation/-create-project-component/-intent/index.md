---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation](../../index.html)/[CreateProjectComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [SetName](-set-name/index.html) |
| [AddLanguage](-add-language/index.html) |
| [SetBaseLanguage](-set-base-language/index.html) |
| [RemoveLanguage](-remove-language/index.html) |
| [Submit](-submit/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddLanguage](-add-language/index.html) | [jvm]<br>data class [AddLanguage](-add-language/index.html)(val value: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) : [CreateProjectComponent.Intent](index.html)<br>Add language. |
| [RemoveLanguage](-remove-language/index.html) | [jvm]<br>data class [RemoveLanguage](-remove-language/index.html)(val value: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) : [CreateProjectComponent.Intent](index.html)<br>Remove a language. |
| [SetBaseLanguage](-set-base-language/index.html) | [jvm]<br>data class [SetBaseLanguage](-set-base-language/index.html)(val value: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) : [CreateProjectComponent.Intent](index.html)<br>Set base language. |
| [SetName](-set-name/index.html) | [jvm]<br>data class [SetName](-set-name/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [CreateProjectComponent.Intent](index.html)<br>Set name. |
| [Submit](-submit/index.html) | [jvm]<br>object [Submit](-submit/index.html) : [CreateProjectComponent.Intent](index.html)<br>Submit the form. |

