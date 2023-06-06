package repository.usecase

import common.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import language.repo.LanguageRepository
import localized
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml
import repository.repo.SegmentRepository
import java.io.File
import java.io.FileWriter

internal class DefaultExportTmxUseCase(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val dispatchers: CoroutineDispatcherProvider,
) : ExportTmxUseCase {

    companion object {
        private const val ELEM_HEADER = "header"
        private const val ELEM_BODY = "body"
        private const val ELEM_UNIT = "tu"
        private const val ELEM_VARIANT = "tuv"
        private const val ELEM_SEGMENT = "seg"
        private const val ATTR_LANGUAGE = "xml:lang"
        private const val ATTR_SOURCE_LANG = "srcLang"
    }

    override suspend operator fun invoke(projectId: Int, path: String) {
        val file = File(path)
        if (!file.exists()) {
            runCatching {
                file.createNewFile()
            }
        }
        if (!file.canWrite()) {
            return
        }
        withContext(dispatchers.io) {
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

            ELEM_HEADER {
                attribute("creationTool", "app_name".localized())
                attribute("creationToolVersion", System.getProperty("jpackage.app-version") ?: "1.0.0")
                attribute("segtype", "sentence")
                attribute("o-tmf", "tmx")
                attribute("adminLang", "en-US")
                attribute(ATTR_SOURCE_LANG, baseLanguage.code)
                attribute("datatype", "plaintext")
            }
            ELEM_BODY {
                for (translationUnit in registry) {
                    ELEM_UNIT {
                        val translationUnitVariants = translationUnit.value
                        for (variant in translationUnitVariants) {
                            ELEM_VARIANT {
                                attribute(ATTR_LANGUAGE, variant.first)
                                ELEM_SEGMENT {
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
