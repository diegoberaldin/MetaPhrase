package com.github.diegoberaldin.metaphrase.domain.mt.repository.data

enum class MachineTranslationProvider {
    MY_MEMORY,
    ;

    val readableName: String
        get() = when (this) {
            MY_MEMORY -> "MyMemory"
            else -> ""
        }
}
