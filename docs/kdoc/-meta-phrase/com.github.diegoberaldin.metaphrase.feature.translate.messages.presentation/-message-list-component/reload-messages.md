---
title: reloadMessages
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation](../index.html)/[MessageListComponent](index.html)/[reloadMessages](reload-messages.html)



# reloadMessages



[jvm]\
abstract fun [reloadMessages](reload-messages.html)(language: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), filter: [TranslationUnitTypeFilter](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))



Reload the message list. It should be called for the first loading operation, for subsequent ones the [refresh](refresh.html) method is enough



#### Parameters


jvm

| | |
|---|---|
| language | current language used in the search |
| filter | message filter used in the search |
| projectId | current project id |




