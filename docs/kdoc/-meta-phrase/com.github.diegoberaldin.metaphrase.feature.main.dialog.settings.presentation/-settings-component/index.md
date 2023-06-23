---
title: SettingsComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation](../index.html)/[SettingsComponent](index.html)



# SettingsComponent



[jvm]\
interface [SettingsComponent](index.html)

Settings component.



## Types


| Name | Summary |
|---|---|
| [DialogConfig](-dialog-config/index.html) | [jvm]<br>interface [DialogConfig](-dialog-config/index.html) : Parcelable<br>Available sub-dialog configurations. |


## Properties


| Name | Summary |
|---|---|
| [dialog](dialog.html) | [jvm]<br>abstract val [dialog](dialog.html): Value&lt;ChildSlot&lt;[SettingsComponent.DialogConfig](-dialog-config/index.html), *&gt;&gt;<br>Current sub-dialog configuration. |
| [languageUiState](language-ui-state.html) | [jvm]<br>abstract val [languageUiState](language-ui-state.html): StateFlow&lt;[SettingsLanguageUiState](../-settings-language-ui-state/index.html)&gt;<br>Language UI state. |
| [machineTranslationUiState](machine-translation-ui-state.html) | [jvm]<br>abstract val [machineTranslationUiState](machine-translation-ui-state.html): StateFlow&lt;[SettingsMachineTranslationUiState](../-settings-machine-translation-ui-state/index.html)&gt;<br>Machine transaltion UI state. |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[SettingsUiState](../-settings-ui-state/index.html)&gt;<br>General UI state. |


## Functions


| Name | Summary |
|---|---|
| [closeDialog](close-dialog.html) | [jvm]<br>abstract fun [closeDialog](close-dialog.html)()<br>Close the current sub-dialog. |
| [generateMachineTranslationKey](generate-machine-translation-key.html) | [jvm]<br>abstract fun [generateMachineTranslationKey](generate-machine-translation-key.html)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Generate a machine translation API key. |
| [openLoginDialog](open-login-dialog.html) | [jvm]<br>abstract fun [openLoginDialog](open-login-dialog.html)()<br>Open the login sub-dialog. |
| [setLanguage](set-language.html) | [jvm]<br>abstract fun [setLanguage](set-language.html)(value: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html))<br>Set language. |
| [setMachineTranslationKey](set-machine-translation-key.html) | [jvm]<br>abstract fun [setMachineTranslationKey](set-machine-translation-key.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set machine translation API key. |
| [setMachineTranslationProvider](set-machine-translation-provider.html) | [jvm]<br>abstract fun [setMachineTranslationProvider](set-machine-translation-provider.html)(index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Set machine translation provider. |
| [setSimilarity](set-similarity.html) | [jvm]<br>abstract fun [setSimilarity](set-similarity.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set similarity threshold |
| [setSpellcheckEnabled](set-spellcheck-enabled.html) | [jvm]<br>abstract fun [setSpellcheckEnabled](set-spellcheck-enabled.html)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Enabled/disabled the spelling check. |

