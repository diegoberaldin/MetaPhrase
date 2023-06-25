package com.github.diegoberaldin.metaphrase.core.common.keystore

import com.github.diegoberaldin.metaphrase.core.common.files.FileManager
import java.io.File

internal object MockFileManager : FileManager {
    private lateinit var file: File

    override fun getFilePath(vararg components: String): String = file.path

    fun setup() {
        file = File.createTempFile("test", ".preferences_pb")
    }

    fun teardown() {
        file.delete()
    }
}