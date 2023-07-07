---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation](../../index.html)/[SettingsComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [SetLanguage](-set-language/index.html) |
| [SetSimilarity](-set-similarity/index.html) |
| [SetSpellcheckEnabled](-set-spellcheck-enabled/index.html) |
| [SetMachineTranslationProvider](-set-machine-translation-provider/index.html) |
| [SetMachineTranslationKey](-set-machine-translation-key/index.html) |
| [OpenLoginDialog](-open-login-dialog/index.html) |
| [CloseDialog](-close-dialog/index.html) |
| [GenerateMachineTranslationKey](-generate-machine-translation-key/index.html) |


## Types


| Name | Summary |
|---|---|
| [CloseDialog](-close-dialog/index.html) | [jvm]<br>object [CloseDialog](-close-dialog/index.html) : [SettingsComponent.Intent](index.html)<br>Close the current sub-dialog. |
| [GenerateMachineTranslationKey](-generate-machine-translation-key/index.html) | [jvm]<br>data class [GenerateMachineTranslationKey](-generate-machine-translation-key/index.html)(val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [SettingsComponent.Intent](index.html)<br>Generate a machine translation API key. |
| [OpenLoginDialog](-open-login-dialog/index.html) | [jvm]<br>object [OpenLoginDialog](-open-login-dialog/index.html) : [SettingsComponent.Intent](index.html)<br>Open the login sub-dialog. |
| [SetLanguage](-set-language/index.html) | [jvm]<br>data class [SetLanguage](-set-language/index.html)(val value: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) : [SettingsComponent.Intent](index.html)<br>Set language. |
| [SetMachineTranslationKey](-set-machine-translation-key/index.html) | [jvm]<br>data class [SetMachineTranslationKey](-set-machine-translation-key/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [SettingsComponent.Intent](index.html)<br>Set machine translation API key. |
| [SetMachineTranslationProvider](-set-machine-translation-provider/index.html) | [jvm]<br>data class [SetMachineTranslationProvider](-set-machine-translation-provider/index.html)(val index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [SettingsComponent.Intent](index.html)<br>Set machine translation provider. |
| [SetSimilarity](-set-similarity/index.html) | [jvm]<br>data class [SetSimilarity](-set-similarity/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [SettingsComponent.Intent](index.html)<br>Set similarity threshold |
| [SetSpellcheckEnabled](-set-spellcheck-enabled/index.html) | [jvm]<br>data class [SetSpellcheckEnabled](-set-spellcheck-enabled/index.html)(val value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [SettingsComponent.Intent](index.html)<br>Enabled/disabled the spelling check. |

