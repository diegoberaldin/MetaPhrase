package translationmemory.usecase

import common.utils.lastPathSegment
import common.utils.stripExtension
import data.TranslationMemoryEntryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import translationmemory.repo.MemoryEntryRepository
import java.io.File

class ImportTmxUseCase(
    private val memoryEntryRepository: MemoryEntryRepository,
) {

    companion object {
        private const val ELEM_HEADER = "header"
        private const val ELEM_BODY = "body"
        private const val ELEM_UNIT = "tu"
        private const val ELEM_VARIANT = "tuv"
        private const val ELEM_SEGMENT = "seg"
        private const val ATTR_LANGUAGE = "xml:lang"
        private const val ATTR_SOURCE_LANG = "srcLang"
    }

    suspend operator fun invoke(path: String) {
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
        return withContext(Dispatchers.IO) {
            runCatching {
                var baseLanguage = ""
                val rootNode = parse(file)
                val headerNode = rootNode.children.firstOrNull { (it as? Node)?.nodeName == ELEM_HEADER } as? Node
                if (headerNode != null) {
                    baseLanguage = (headerNode.attributes[ATTR_SOURCE_LANG] as? String).orEmpty()
                }
                val unitList = mutableListOf<List<Pair<String, String>>>()

                val bodyNode = rootNode.children.firstOrNull { (it as? Node)?.nodeName == ELEM_BODY } as? Node
                if (bodyNode != null) {
                    for (unit in bodyNode.children.filter { (it as? Node)?.nodeName == ELEM_UNIT }) {
                        val variantList = mutableListOf<Pair<String, String>>()
                        for (variant in (unit as Node).children.filter { (it as? Node)?.nodeName == ELEM_VARIANT }) {
                            val lang = ((variant as Node).attributes[ATTR_LANGUAGE] as? String).orEmpty()
                            val segment =
                                variant.children.firstOrNull { (it as? Node)?.nodeName == ELEM_SEGMENT } as? Node
                            val text = (segment?.children?.firstOrNull() as? TextElement)?.text.orEmpty()
                            variantList += (lang to text)
                        }
                        unitList += variantList
                    }
                }

                val res = mutableListOf<TranslationMemoryEntryModel>()
                for (unit in unitList) {
                    val sourceText = unit.firstOrNull { it.first == baseLanguage }?.second.orEmpty()
                    if (sourceText.isEmpty()) continue

                    for (targetVariant in unit.filter { it.first != baseLanguage }) {
                        val targetLang = targetVariant.first
                        val targetText = targetVariant.second
                        res += TranslationMemoryEntryModel(
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
