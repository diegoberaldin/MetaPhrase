---
title: LogManager
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.log](../index.html)/[LogManager](index.html)



# LogManager



[jvm]\
interface [LogManager](index.html)

Abstract log manager.



## Functions


| Name | Summary |
|---|---|
| [debug](debug.html) | [jvm]<br>abstract fun [debug](debug.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Log a message with the DEBUG level. |
| [error](error.html) | [jvm]<br>abstract fun [error](error.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Log a message with the ERROR level. |
| [exception](exception.html) | [jvm]<br>abstract fun [exception](exception.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html))<br>Log an exception. |
| [info](info.html) | [jvm]<br>abstract fun [info](info.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Log a message with the INFO level. |
| [trace](trace.html) | [jvm]<br>abstract fun [trace](trace.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Log a message with the TRACE level. |
| [warn](warn.html) | [jvm]<br>abstract fun [warn](warn.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Log a message with the WARNING level. |

