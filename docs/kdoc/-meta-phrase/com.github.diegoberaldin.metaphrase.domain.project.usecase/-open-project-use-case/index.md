---
title: OpenProjectUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.usecase](../index.html)/[OpenProjectUseCase](index.html)



# OpenProjectUseCase



[jvm]\
interface [OpenProjectUseCase](index.html)

Contract for the open project use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)?<br>Open a project saved on a given path as a TMX file. Opening implies that all the languages and segments of that specific project will be loaded in the application DB for subsequent queries. |

