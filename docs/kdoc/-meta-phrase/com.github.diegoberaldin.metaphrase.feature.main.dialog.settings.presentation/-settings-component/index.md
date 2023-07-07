---
title: SettingsComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation](../index.html)/[SettingsComponent](index.html)



# SettingsComponent



[jvm]\
interface [SettingsComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[SettingsComponent.Intent](-intent/index.html), [SettingsComponent.UiState](-ui-state/index.html), [SettingsComponent.Effect](-effect/index.html)&gt; 

Settings component contract.



## Types


| Name | Summary |
|---|---|
| [DialogConfig](-dialog-config/index.html) | [jvm]<br>interface [DialogConfig](-dialog-config/index.html) : Parcelable<br>Available sub-dialog configurations. |
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val availableLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val currentLanguage: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val similarityThreshold: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val spellcheckEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val appVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val availableProviders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)&gt; = emptyList(), val currentProvider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html)? = null, val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>UI state for general settings. |


## Properties


| Name | Summary |
|---|---|
| [dialog](dialog.html) | [jvm]<br>abstract val [dialog](dialog.html): Value&lt;ChildSlot&lt;[SettingsComponent.DialogConfig](-dialog-config/index.html), *&gt;&gt;<br>Current sub-dialog configuration. |
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[SettingsComponent.Effect](-effect/index.html)&gt; |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[SettingsComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#-1298201823%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#-1298201823%2FFunctions%2F2137835383)(intent: [SettingsComponent.Intent](-intent/index.html)) |

