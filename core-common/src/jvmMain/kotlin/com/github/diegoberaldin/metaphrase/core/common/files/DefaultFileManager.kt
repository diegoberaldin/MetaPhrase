package com.github.diegoberaldin.metaphrase.core.common.files

import java.io.File
import java.util.*

object DefaultFileManager : com.github.diegoberaldin.metaphrase.core.common.files.FileManager {

    private const val DIR_NAME = "MetaPhrase"
    private val separator = System.getProperty("file.separator")

    private lateinit var dirPath: String

    init {
        com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.ensureAppDirectory()
    }

    override fun getFilePath(vararg components: String): String {
        return com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.dirPath + com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.separator + components.joinToString(
            separator = com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.separator,
        )
    }

    private fun ensureAppDirectory() {
        val os = System.getProperty("os.name").uppercase(Locale.getDefault())
        if (os.contains("WIN")) {
            com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.dirPath =
                System.getenv("APPDATA") + com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.separator + com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.DIR_NAME
        }
        if (os.contains("MAC")) {
            com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.dirPath =
                System.getProperty("user.home") +
                "${com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.separator}Library" + "${com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.separator}Application Support${com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.separator}" + com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.DIR_NAME
        }
        if (os.contains("NUX")) {
            com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.dirPath =
                System.getProperty("user.dir") + com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.separator + ".${com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.DIR_NAME}"
        }

        val directory = File(com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager.dirPath)
        if (!directory.exists()) {
            directory.mkdir()
        }
    }
}
