---
title: TranslateEditableField
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.ui](index.html)/[TranslateEditableField](-translate-editable-field.html)



# TranslateEditableField



[jvm]\




@Composable



fun [TranslateEditableField](-translate-editable-field.html)(unit: [TranslationUnit](../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html), focusRequester: FocusRequester = remember { FocusRequester() }, active: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, updateTextSwitch: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, spellingErrors: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../com.github.diegoberaldin.metaphrase.domain.spellcheck.data/-spell-check-correction/index.html)&gt; = emptyList(), onStartEditing: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}, onTextChanged: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}, onAddToGlossary: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}, onIgnoreWord: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {})



Translation editable field.



#### Parameters


jvm

| | |
|---|---|
| unit | translation unit |
| focusRequester | focus requester |
| active | flag indicating whether this is the currently edited segment |
| updateTextSwitch | flag to trigger text updates programmatically |
| enabled | flag indicating whether this field should be enabled |
| spellingErrors | list of spelling errors detected |
| onStartEditing | on start editing callback |
| onTextChanged | on text changed callback |
| onAddToGlossary | on add to glossary callback |
| onIgnoreWord | on ignore word callback |




