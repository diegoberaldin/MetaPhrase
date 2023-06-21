package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml
import java.io.File
import java.io.FileWriter

internal class DefaultExportTmxUseCase(
    private val memoryEntryRepository: MemoryEntryRepository,
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
        private const val ATTR_UNIT_ID = "tuid"
    }

    private data class LocalizedMessage(
        val lang: String,
        val message: String,
    )

    override suspend operator fun invoke(sourceLang: String, path: String) {
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
                val content = getXml(sourceLang)
                FileWriter(file).use {
                    it.write(content)
                }
            }
        }
    }

    private suspend fun getXml(sourceLang: String): String {
        val registry = mutableMapOf<String, List<LocalizedMessage>>()

        val otherLanguages = memoryEntryRepository.getLanguageCodes().filter { it != sourceLang }
        val sourceMessages = memoryEntryRepository.getEntries(sourceLang)
        sourceMessages.forEach {
            val key = it.identifier
            registry[key] = buildList {
                this += LocalizedMessage(lang = sourceLang, message = it.sourceText)
                for (lang in otherLanguages) {
                    val localMessage = memoryEntryRepository.getTranslation(lang = lang, key = key)
                    if (localMessage != null && localMessage.targetText.isNotEmpty()) {
                        this += LocalizedMessage(lang = lang, message = localMessage.targetText)
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
                attribute(ATTR_SOURCE_LANG, sourceLang)
                attribute("datatype", "plaintext")
            }
            ELEM_BODY {
                for (translationUnit in registry) {
                    ELEM_UNIT {
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
