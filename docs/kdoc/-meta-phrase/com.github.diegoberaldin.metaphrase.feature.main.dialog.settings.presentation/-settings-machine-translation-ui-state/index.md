---
title: SettingsMachineTranslationUiState
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation](../index.html)/[SettingsMachineTranslationUiState](index.html)



# SettingsMachineTranslationUiState



[jvm]\
data class [SettingsMachineTranslationUiState](index.html)(val availableProviders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)&gt; = emptyList(), val currentProvider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)? = null, val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

UI state for machine transaltion



## Constructors


| | |
|---|---|
| [SettingsMachineTranslationUiState](-settings-machine-translation-ui-state.html) | [jvm]<br>constructor(availableProviders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)&gt; = emptyList(), currentProvider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)? = null, key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>Create [SettingsMachineTranslationUiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [availableProviders](available-providers.html) | [jvm]<br>val [availableProviders](available-providers.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)&gt;<br>available Machine Translation providers |
| [currentProvider](current-provider.html) | [jvm]<br>val [currentProvider](current-provider.html): [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)? = null<br>current MT provider |
| [key](key.html) | [jvm]<br>val [key](key.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>API key |

