---
title: NotificationCenter
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.notification](../index.html)/[NotificationCenter](index.html)



# NotificationCenter

interface [NotificationCenter](index.html)

Utility to publish and subscribe for broadcast notifications.



#### Inheritors


| |
|---|
| [DefaultNotificationCenter](../-default-notification-center/index.html) |


## Types


| Name | Summary |
|---|---|
| [Event](-event/index.html) | [jvm]<br>interface [Event](-event/index.html)<br>Available event types. |


## Properties


| Name | Summary |
|---|---|
| [events](events.html) | [jvm]<br>abstract val [events](events.html): SharedFlow&lt;[NotificationCenter.Event](-event/index.html)&gt;<br>Observable event flow |


## Functions


| Name | Summary |
|---|---|
| [send](send.html) | [jvm]<br>abstract fun [send](send.html)(event: [NotificationCenter.Event](-event/index.html))<br>Publish and event to subscribers. |

