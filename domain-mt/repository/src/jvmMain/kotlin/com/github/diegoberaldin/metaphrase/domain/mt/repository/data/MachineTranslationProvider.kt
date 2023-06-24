package com.github.diegoberaldin.metaphrase.domain.mt.repository.data

/**
 * Machine translation providers.
 *
 * @constructor Create empty constructor for machine translation provider
 */
enum class MachineTranslationProvider {
    MY_MEMORY,
    ;

    val readableName: String
        get() = when (this) {
            MY_MEMORY -> "MyMemory"
            else -> ""
        }
}
