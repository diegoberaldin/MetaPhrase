---
title: asFlow
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.utils](index.html)/[asFlow](as-flow.html)



# asFlow



[jvm]\
inline fun &lt;[T](as-flow.html)&gt; Value&lt;ChildSlot&lt;*, *&gt;&gt;.[asFlow](as-flow.html)(withNullsIfNotInstance: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, timeout: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html) = 500.milliseconds): Flow&lt;[T](as-flow.html)?&gt;



Observe the instance of a child slot as a flow.



#### Receiver



Value Original slot



#### Return



Flow Flow of instance



#### Parameters


jvm

| | |
|---|---|
| T | Type of the result |
| withNullsIfNotInstance | Emits null if the current value is not of the correct type |
| timeout | Timeout to wait for emission |




