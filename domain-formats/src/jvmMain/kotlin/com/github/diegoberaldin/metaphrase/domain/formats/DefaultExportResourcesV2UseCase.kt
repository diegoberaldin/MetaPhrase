package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.core.common.utils.lastPathSegment
import com.github.diegoberaldin.metaphrase.core.common.utils.stripExtension
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively

class DefaultExportResourcesV2UseCase(
    private val baseUseCase: ExportResourcesUseCase,
) : ExportResourcesV2UseCase {
    @OptIn(ExperimentalPathApi::class)
    override suspend fun invoke(data: Map<String, List<SegmentModel>>, path: String, type: ResourceFileType) {
        val tempDir = Files.createTempDirectory("")
        val resourcesDir = File(tempDir.toFile(), path.lastPathSegment().stripExtension())

        ZipOutputStream(BufferedOutputStream(FileOutputStream(path))).use { ostream ->
            for ((lang, segments) in data) {
                val langDirectory = File(
                    resourcesDir,
                    when (type) {
                        ResourceFileType.ANDROID_XML -> "values-$lang"
                        ResourceFileType.IOS_STRINGS -> "$lang.lproj"
                        else -> lang
                    },
                )
                val filename = when (type) {
                    ResourceFileType.IOS_STRINGS -> "Localizables"
                    ResourceFileType.RESX -> "Resources"
                    ResourceFileType.PO -> "messages"
                    ResourceFileType.PROPERTIES -> "dictionary"
                    else -> "strings"
                }
                val extension = when (type) {
                    ResourceFileType.ANDROID_XML -> ".xml"
                    ResourceFileType.IOS_STRINGS -> ".strings"
                    ResourceFileType.RESX -> ".resx"
                    ResourceFileType.PO -> ".po"
                    ResourceFileType.JSON -> ".json"
                    ResourceFileType.ARB -> ".arb"
                    ResourceFileType.PROPERTIES -> ".properties"
                    else -> ""
                }
                langDirectory.mkdirs()
                val file = File(langDirectory, "$filename$extension")

                baseUseCase(
                    segments = segments,
                    path = file.path,
                    lang = lang,
                    type = type,
                )

                langDirectory.mkdirs()
                file.createNewFile()

                FileInputStream(file).use { fileInputStream ->
                    BufferedInputStream(fileInputStream).use { origin ->
                        val filePath = file.absolutePath
                        val index = filePath.indexOf(
                            System.getProperty("file.separator") + resourcesDir.name + System.getProperty("file.separator"),
                        )
                        val entryName = filePath.substring(startIndex = index)
                        val entry = ZipEntry(entryName)
                        ostream.putNextEntry(entry)
                        origin.copyTo(ostream, 1024)
                    }
                }
            }
        }

        runCatching {
            tempDir.deleteRecursively()
        }
    }
}
