---
title: FileManager
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.files](../index.html)/[FileManager](index.html)



# FileManager

interface [FileManager](index.html)

Utility to access files on disk.



#### Inheritors


| |
|---|
| [DefaultFileManager](../-default-file-manager/index.html) |


## Functions


| Name | Summary |
|---|---|
| [getFilePath](get-file-path.html) | [jvm]<br>abstract fun [getFilePath](get-file-path.html)(vararg components: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get the path for the given components in a private application space on disk. The output will vary depending on the platform, but it can be assumed to be writable and readable. |

