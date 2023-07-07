---
title: com.github.diegoberaldin.metaphrase.core.common.architecture
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.architecture](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [DefaultMviModel](-default-mvi-model/index.html) | [jvm]<br>class [DefaultMviModel](-default-mvi-model/index.html)&lt;[Intent](-default-mvi-model/index.html), [State](-default-mvi-model/index.html), [Effect](-default-mvi-model/index.html)&gt;(initialState: [State](-default-mvi-model/index.html)) : [MviModel](-mvi-model/index.html)&lt;[Intent](-default-mvi-model/index.html), [State](-default-mvi-model/index.html), [Effect](-default-mvi-model/index.html)&gt; <br>Basic implementation of the MVI model. This is useful to easily implement the interface by delegation, minimizing the amount of code that is needed when integrating the MVI pattern. The [updateState](-default-mvi-model/update-state.html) and [emitEffect](-default-mvi-model/emit-effect.html) methods are shortcuts to easily update the UI state and emit a side effect. |
| [MviModel](-mvi-model/index.html) | [jvm]<br>interface [MviModel](-mvi-model/index.html)&lt;[Intent](-mvi-model/index.html), [State](-mvi-model/index.html), [Effect](-mvi-model/index.html)&gt;<br>Model contract for Model-View-Intent architecture. |

