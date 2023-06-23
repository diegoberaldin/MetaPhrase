---
title: LoginComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.feature.main.settings.dialog.login.presentation](../index.html)/[LoginComponent](index.html)



# LoginComponent



[jvm]\
interface [LoginComponent](index.html)

Login component.



## Properties


| Name | Summary |
|---|---|
| [done](done.html) | [jvm]<br>abstract val [done](done.html): SharedFlow&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;<br>Submission events (first element: username, second element: password). |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[LoginUiState](../-login-ui-state/index.html)&gt;<br>UI state. |


## Functions


| Name | Summary |
|---|---|
| [setPassword](set-password.html) | [jvm]<br>abstract fun [setPassword](set-password.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set password. |
| [setUsername](set-username.html) | [jvm]<br>abstract fun [setUsername](set-username.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set username. |
| [submit](submit.html) | [jvm]<br>abstract fun [submit](submit.html)()<br>Submit the currently inserted values. |

