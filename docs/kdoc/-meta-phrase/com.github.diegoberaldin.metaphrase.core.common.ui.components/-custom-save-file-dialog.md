---
title: CustomSaveFileDialog
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.ui.components](index.html)/[CustomSaveFileDialog](-custom-save-file-dialog.html)



# CustomSaveFileDialog



[jvm]\




@Composable



fun [CustomSaveFileDialog](-custom-save-file-dialog.html)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, initialFileName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, nameFilter: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = { true }, parent: [Frame](https://docs.oracle.com/javase/8/docs/api/java/awt/Frame.html)? = null, onCloseRequest: (result: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))



Custom save file dialog.



#### Parameters


jvm

| | |
|---|---|
| title | Dialog title |
| initialFileName | Initial file name |
| nameFilter | Name filter (return true if and only if the name matches the wanted format) |
| parent | Parent |
| onCloseRequest | On close callback |




