package com.github.diegoberaldin.metaphrase.core.localization.data

/**
 * Representation of a UI message.
 *
 * @property key Message key (ID)
 * @property value Message translation
 * @constructor Create [LocalizableString]
 */
data class LocalizableString(val key: String, val value: String)
