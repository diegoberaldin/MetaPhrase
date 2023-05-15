package common.files

import java.io.File
import java.util.*

object DefaultFileManager : common.files.FileManager {

    private const val DIR_NAME = "MetaLine"
    private val separator = System.getProperty("file.separator")

    private lateinit var dirPath: String

    init {
        common.files.DefaultFileManager.ensureAppDirectory()
    }

    override fun getFilePath(vararg components: String): String {
        return common.files.DefaultFileManager.dirPath + common.files.DefaultFileManager.separator + components.joinToString(separator = common.files.DefaultFileManager.separator)
    }

    private fun ensureAppDirectory() {
        val os = System.getProperty("os.name").uppercase(Locale.getDefault())
        if (os.contains("WIN")) {
            common.files.DefaultFileManager.dirPath = System.getenv("APPDATA") + common.files.DefaultFileManager.separator + common.files.DefaultFileManager.DIR_NAME
        }
        if (os.contains("MAC")) {
            common.files.DefaultFileManager.dirPath =
                System.getProperty("user.home") +
                "${common.files.DefaultFileManager.separator}Library" + "${common.files.DefaultFileManager.separator}Application Support${common.files.DefaultFileManager.separator}" + common.files.DefaultFileManager.DIR_NAME
        }
        if (os.contains("NUX")) {
            common.files.DefaultFileManager.dirPath = System.getProperty("user.dir") + common.files.DefaultFileManager.separator + ".${common.files.DefaultFileManager.DIR_NAME}"
        }

        val directory = File(common.files.DefaultFileManager.dirPath)
        if (!directory.exists()) {
            directory.mkdir()
        }
    }
}
