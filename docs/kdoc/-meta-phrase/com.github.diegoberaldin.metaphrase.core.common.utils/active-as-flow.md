---
title: activeAsFlow
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.utils](index.html)/[activeAsFlow](active-as-flow.html)



# activeAsFlow



[jvm]\
inline fun &lt;[T](active-as-flow.html)&gt; Value&lt;ChildStack&lt;*, *&gt;&gt;.[activeAsFlow](active-as-flow.html)(withNullsIfNotInstance: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, timeout: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html) = 500.milliseconds): Flow&lt;[T](active-as-flow.html)?&gt;



Observe the instance of a child stack as a flow.



#### Receiver



Value original stack



#### Return



Flow



#### Parameters


jvm

| | |
|---|---|
| T | Type of the result |
| withNullsIfNotInstance | emits null if the active value is not of the correct type |
| timeout | Timeout to wait for emission |




