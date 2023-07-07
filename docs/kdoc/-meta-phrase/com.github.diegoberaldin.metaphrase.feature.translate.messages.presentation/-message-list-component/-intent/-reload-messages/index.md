---
title: ReloadMessages
---
//[MetaPhrase](../../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation](../../../index.html)/[MessageListComponent](../../index.html)/[Intent](../index.html)/[ReloadMessages](index.html)



# ReloadMessages

data class [ReloadMessages](index.html)(val language: [LanguageModel](../../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), val filter: [TranslationUnitTypeFilter](../../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html), val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [MessageListComponent.Intent](../index.html)

Reload the message list. It should be called for the first loading operation, for subsequent ones the [Refresh](../-refresh/index.html) intent is enough



#### Parameters


jvm

| | |
|---|---|
| language | current language used in the search |
| filter | message filter used in the search |
| projectId | current project id |



## Constructors


| | |
|---|---|
| [ReloadMessages](-reload-messages.html) | [jvm]<br>constructor(language: [LanguageModel](../../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), filter: [TranslationUnitTypeFilter](../../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [filter](filter.html) | [jvm]<br>val [filter](filter.html): [TranslationUnitTypeFilter](../../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html) |
| [language](language.html) | [jvm]<br>val [language](language.html): [LanguageModel](../../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html) |
| [projectId](project-id.html) | [jvm]<br>val [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

