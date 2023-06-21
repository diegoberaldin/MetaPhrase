package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml
import java.io.File
import java.io.FileWriter

internal class DefaultSaveProjectUseCase(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val dispatchers: CoroutineDispatcherProvider,
) : SaveProjectUseCase {
    companion object {
        private const val ELEM_HEADER = "header"
        private const val ELEM_BODY = "body"
        private const val ELEM_UNIT = "tu"
        private const val ELEM_VARIANT = "tuv"
        private const val ELEM_SEGMENT = "seg"
        private const val ATTR_LANGUAGE = "xml:lang"
        private const val ATTR_SOURCE_LANG = "srcLang"
        private const val ATTR_UNIT_ID = "tuid"
    }

    private data class LocalizedMessage(
        val lang: String,
        val message: String,
    )

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

        val registry = mutableMapOf<String, List<LocalizedMessage>>()
        segmentRepository.getAll(baseLanguage.id).forEach { baseSegment ->
            val key = baseSegment.key
            registry[key] = buildList {
                this += LocalizedMessage(lang = baseLanguage.code, message = baseSegment.text)
                if (baseSegment.translatable) {
                    for (lang in otherLanguages) {
                        val localSegment = segmentRepository.getByKey(key = key, languageId = lang.id)
                        if (localSegment != null) {
                            this += LocalizedMessage(lang = lang.code, message = localSegment.text)
                        }
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
                        attribute(ATTR_UNIT_ID, translationUnit.key)
                        val translationUnitVariants = translationUnit.value
                        for (variant in translationUnitVariants) {
                            ELEM_VARIANT {
                                attribute(ATTR_LANGUAGE, variant.lang)
                                ELEM_SEGMENT {
                                    text(variant.message)
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
