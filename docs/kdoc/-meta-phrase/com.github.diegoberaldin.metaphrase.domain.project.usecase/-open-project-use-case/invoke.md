---
title: invoke
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.usecase](../index.html)/[OpenProjectUseCase](index.html)/[invoke](invoke.html)



# invoke



[jvm]\
abstract suspend operator fun [invoke](invoke.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)?



Open a project saved on a given path as a TMX file. Opening implies that all the languages and segments of that specific project will be loaded in the application DB for subsequent queries.



#### Return



[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html) or null if there was an error



#### Parameters


jvm

| | |
|---|---|
| path | Path of the TMX file |




