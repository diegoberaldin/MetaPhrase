package com.github.diegoberaldin.metaphrase.core.common.testutils

import com.github.diegoberaldin.metaphrase.core.common.files.FileManager
import java.io.File

object MockFileManager : FileManager {
    private lateinit var file: File

    override fun getFilePath(vararg components: String): String = file.path

    fun setup(name: String, extension: String) {
        file = File.createTempFile(name, extension)
    }

    fun teardown() {
        file.delete()
    }
}
