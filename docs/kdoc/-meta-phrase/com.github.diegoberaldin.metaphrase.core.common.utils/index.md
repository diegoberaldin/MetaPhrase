---
title: com.github.diegoberaldin.metaphrase.core.common.utils
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.utils](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [Constants](-constants/index.html) | [jvm]<br>object [Constants](-constants/index.html) |
| [LruCache](-lru-cache/index.html) | [jvm]<br>class [LruCache](-lru-cache/index.html)&lt;[T](-lru-cache/index.html)&gt;(size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [activeAsFlow](active-as-flow.html) | [jvm]<br>inline fun &lt;[T](active-as-flow.html)&gt; Value&lt;ChildStack&lt;*, *&gt;&gt;.[activeAsFlow](active-as-flow.html)(withNullsIfNotInstance: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, timeout: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html) = 500.milliseconds): Flow&lt;[T](active-as-flow.html)?&gt; |
| [asFlow](as-flow.html) | [jvm]<br>inline fun &lt;[T](as-flow.html)&gt; Value&lt;ChildSlot&lt;*, *&gt;&gt;.[asFlow](as-flow.html)(withNullsIfNotInstance: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, timeout: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html) = 500.milliseconds): Flow&lt;[T](as-flow.html)?&gt; |
| [combine](combine.html) | [jvm]<br>inline fun &lt;[T1](combine.html), [T2](combine.html), [T3](combine.html), [T4](combine.html), [T5](combine.html), [T6](combine.html), [R](combine.html)&gt; [combine](combine.html)(flow: Flow&lt;[T1](combine.html)&gt;, flow2: Flow&lt;[T2](combine.html)&gt;, flow3: Flow&lt;[T3](combine.html)&gt;, flow4: Flow&lt;[T4](combine.html)&gt;, flow5: Flow&lt;[T5](combine.html)&gt;, flow6: Flow&lt;[T6](combine.html)&gt;, crossinline transform: suspend ([T1](combine.html), [T2](combine.html), [T3](combine.html), [T4](combine.html), [T5](combine.html), [T6](combine.html)) -&gt; [R](combine.html)): Flow&lt;[R](combine.html)&gt; |
| [getByInjection](get-by-injection.html) | [jvm]<br>inline fun &lt;[T](get-by-injection.html)&gt; [getByInjection](get-by-injection.html)(): [T](get-by-injection.html)<br>inline fun &lt;[T](get-by-injection.html)&gt; [getByInjection](get-by-injection.html)(vararg params: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [T](get-by-injection.html) |
| [lastPathSegment](last-path-segment.html) | [jvm]<br>fun [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[lastPathSegment](last-path-segment.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [runOnUiThread](run-on-ui-thread.html) | [jvm]<br>fun &lt;[T](run-on-ui-thread.html)&gt; [runOnUiThread](run-on-ui-thread.html)(block: () -&gt; [T](run-on-ui-thread.html)): [T](run-on-ui-thread.html) |
| [stripExtension](strip-extension.html) | [jvm]<br>fun [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[stripExtension](strip-extension.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toLocalPixel](to-local-pixel.html) | [jvm]<br>@Composable<br>fun Dp.[toLocalPixel](to-local-pixel.html)(): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [toRadians](to-radians.html) | [jvm]<br>fun [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html).[toRadians](to-radians.html)(): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |

