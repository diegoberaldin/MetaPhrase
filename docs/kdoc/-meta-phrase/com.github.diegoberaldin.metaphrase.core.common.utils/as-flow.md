---
title: asFlow
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.utils](index.html)/[asFlow](as-flow.html)



# asFlow



[jvm]\
inline fun &lt;[T](as-flow.html)&gt; Value&lt;ChildSlot&lt;*, *&gt;&gt;.[asFlow](as-flow.html)(withNullsIfNotInstance: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, timeout: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html) = 500.milliseconds): Flow&lt;[T](as-flow.html)?&gt;



Observe a child slot as a flow.



#### Receiver



Value original slot



#### Return



Flow



#### Parameters


jvm

| | |
|---|---|
| T | Type of the result |
| withNullsIfNotInstance | emits null if the active value is not of the correct type |
| timeout | Timeout to wait for emission |




