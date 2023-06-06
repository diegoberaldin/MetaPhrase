package android.usecase

import data.SegmentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.xml
import java.io.File
import java.io.FileWriter

internal class DefaultExportAndroidResourcesUseCase : ExportAndroidResourcesUseCase {
    override suspend operator fun invoke(segments: List<SegmentModel>, path: String) {
        val file = File(path)
        if (!file.exists()) {
            runCatching {
                file.createNewFile()
            }
        }
        if (!file.canWrite()) {
            return
        }
        withContext(Dispatchers.IO) {
            runCatching {
                val content = getXml(segments)
                FileWriter(file).use {
                    it.write(content)
                }
            }
        }
    }

    private fun getXml(segments: List<SegmentModel>): String {
        val root = xml("resources") {
            includeXmlProlog = true
            encoding = "UTF-8"

            for (segment in segments) {
                "string" {
                    attribute("name", segment.key)
                    if (!segment.translatable) {
                        attribute("translatable", false)
                    }
                    text(segment.text)
                }
            }
        }
        return root.toString(PrintOptions(pretty = true, singleLineTextElements = true, useCharacterReference = false))
            .sanitize()
    }

    private fun String.sanitize(): String {
        val substitutionList = listOf(
            "&quot;" to "\"",
            "&apos;" to "\\'",
            "&gt;" to ">",
            "&lt;" to "<",
        )
        return substitutionList.fold(this) { res, it ->
            res.replace(it.first, it.second)
        }
    }
}
