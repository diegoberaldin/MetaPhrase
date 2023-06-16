package com.github.diegoberaldin.metaphrase.core.common.files

interface FileManager {
    fun getFilePath(vararg components: String): String
}
