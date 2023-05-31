package repository.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import localized
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml
import repository.local.LanguageRepository
import repository.local.SegmentRepository
import java.io.File
import java.io.FileWriter

class ExportTmxUseCase(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) {
    suspend operator fun invoke(projectId: Int, path: String) {
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
                val content = getXml(projectId)
                FileWriter(file).use {
                    it.write(content)
                }
            }
        }
    }

    private suspend fun getXml(projectId: Int): String {
        val baseLanguage = languageRepository.getBase(projectId) ?: return ""
        val otherLanguages = languageRepository.getAll(projectId).filter { it.code != baseLanguage.code }

        val registry = mutableMapOf<String, List<Pair<String, String>>>()
        segmentRepository.getAll(baseLanguage.id).forEach { baseSegment ->
            val key = baseSegment.key
            registry[key] = buildList {
                this += baseLanguage.code to baseSegment.text
                for (lang in otherLanguages) {
                    val localSegment = segmentRepository.getByKey(key = key, languageId = lang.id)
                    if (localSegment != null && localSegment.text.isNotEmpty()) {
                        this += lang.code to localSegment.text
                    }
                }
            }
        }

        val root = xml("tmx") {
            includeXmlProlog = true
            encoding = "UTF-8"
            version = XmlVersion.V10

            attribute("version", "1.4")

            "header" {
                attribute("creationTool", "app_name".localized())
                attribute("creationToolVersion", "1.0.0")
                attribute("segtype", "sentence")
                attribute("o-tmf", "tmx")
                attribute("adminLang", "en-US")
                attribute("srcLang", baseLanguage.code)
                attribute("datatype", "plaintext")
            }
            "body" {
                for (translationUnit in registry) {
                    "tu" {
                        val translationUnitVariants = translationUnit.value
                        for (variant in translationUnitVariants) {
                            "tuv" {
                                attribute("xml:lang", variant.first)
                                "seg" {
                                    text(variant.second)
                                }
                            }
                        }
                    }
                }
            }
        }
        return root.toString(PrintOptions(pretty = true))
    }
}
