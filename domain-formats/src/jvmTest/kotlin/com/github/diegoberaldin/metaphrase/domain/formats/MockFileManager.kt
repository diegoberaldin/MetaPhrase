package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.core.common.files.FileManager
import java.io.File

internal object MockFileManager : FileManager {
    private lateinit var file: File

    override fun getFilePath(vararg components: String): String = file.path

    fun setup(name: String, extension: String) {
        file = File.createTempFile(name, extension)
    }

    fun teardown() {
        file.delete()
    }
}
