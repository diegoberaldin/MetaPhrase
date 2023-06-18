package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.lastPathSegment
import com.github.diegoberaldin.metaphrase.core.common.utils.stripExtension
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import java.io.File

internal class DefaultImportTmxUseCase(
    private val memoryEntryRepository: MemoryEntryRepository,
    private val dispatchers: CoroutineDispatcherProvider,
) : ImportTmxUseCase {

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

    override suspend operator fun invoke(path: String) {
        val entries = readEntries(path)
        for (entry in entries) {
            memoryEntryRepository.create(entry)
        }
    }

    private suspend fun readEntries(path: String): List<TranslationMemoryEntryModel> {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return emptyList()
        }
        val origin = path.lastPathSegment().stripExtension()
        return withContext(dispatchers.io) {
            runCatching {
                var baseLanguage = ""
                val rootNode = parse(file)
                val headerNode = rootNode.children.firstOrNull { (it as? Node)?.nodeName == ELEM_HEADER } as? Node
                if (headerNode != null) {
                    baseLanguage = (headerNode.attributes[ATTR_SOURCE_LANG] as? String).orEmpty()
                }
                val unitMap = mutableMapOf<String, List<LocalizedMessage>>()

                val bodyNode = rootNode.children.firstOrNull { (it as? Node)?.nodeName == ELEM_BODY } as? Node
                if (bodyNode != null) {
                    for (unit in bodyNode.children.filter { (it as? Node)?.nodeName == ELEM_UNIT }) {
                        val key = ((unit as Node).attributes[ATTR_UNIT_ID] as? String).orEmpty()
                        val variantList = mutableListOf<LocalizedMessage>()
                        for (variant in unit.children.filter { (it as? Node)?.nodeName == ELEM_VARIANT }) {
                            val lang = ((variant as Node).attributes[ATTR_LANGUAGE] as? String).orEmpty()
                            val segment =
                                variant.children.firstOrNull { (it as? Node)?.nodeName == ELEM_SEGMENT } as? Node
                            val text = (segment?.children?.firstOrNull() as? TextElement)?.text.orEmpty()
                            variantList += LocalizedMessage(lang, text)
                        }
                        unitMap[key] = variantList
                    }
                }

                val res = mutableListOf<TranslationMemoryEntryModel>()
                for (unit in unitMap) {
                    val identifier = unit.key
                    val sourceText = unit.value.firstOrNull { it.lang == baseLanguage }?.message.orEmpty()
                    if (sourceText.isEmpty()) continue

                    for (targetVariant in unit.value.filter { it.lang != baseLanguage }) {
                        val targetLang = targetVariant.lang
                        val targetText = targetVariant.message
                        res += TranslationMemoryEntryModel(
                            identifier = identifier,
                            sourceText = sourceText,
                            sourceLang = baseLanguage,
                            targetText = targetText,
                            targetLang = targetLang,
                            origin = origin,
                        )
                    }
                }
                res
            }.getOrElse { emptyList() }
        }
    }
}
