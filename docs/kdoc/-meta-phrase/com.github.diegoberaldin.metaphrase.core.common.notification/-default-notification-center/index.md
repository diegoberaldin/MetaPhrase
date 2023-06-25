---
title: DefaultNotificationCenter
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.notification](../index.html)/[DefaultNotificationCenter](index.html)



# DefaultNotificationCenter



[jvm]\
object [DefaultNotificationCenter](index.html) : [NotificationCenter](../-notification-center/index.html)



## Properties


| Name | Summary |
|---|---|
| [events](events.html) | [jvm]<br>open override val [events](events.html): MutableSharedFlow&lt;[NotificationCenter.Event](../-notification-center/-event/index.html)&gt;<br>Observable event flow |


## Functions


| Name | Summary |
|---|---|
| [send](send.html) | [jvm]<br>open override fun [send](send.html)(event: [NotificationCenter.Event](../-notification-center/-event/index.html))<br>Publish and event to subscribers. |

