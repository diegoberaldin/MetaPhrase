package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.lastPathSegment
import com.github.diegoberaldin.metaphrase.core.common.utils.stripExtension
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import java.io.File

internal class DefaultOpenProjectUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) : OpenProjectUseCase {

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

    override suspend fun invoke(path: String): ProjectModel? {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return null
        }
        return withContext(dispatchers.io) {
            val name = path.lastPathSegment().stripExtension()
            val project = ProjectModel(name = name)
            val projectId = projectRepository.create(project)

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

            val registry = mutableMapOf<String, MutableList<SegmentModel>>()

            for (unit in unitMap) {
                val key = unit.key
                val sourceVariant = unit.value.firstOrNull { it.lang == baseLanguage }
                val otherVariants = unit.value.filter { it.lang != baseLanguage }
                if (sourceVariant != null && sourceVariant.message.isNotEmpty()) {
                    val segment = SegmentModel(
                        key = key,
                        text = sourceVariant.message,
                        translatable = otherVariants.isNotEmpty(),
                    )
                    if (registry[baseLanguage] == null) {
                        registry[baseLanguage] = mutableListOf()
                    }
                    (registry[baseLanguage] as MutableList<SegmentModel>) += segment
                }
                for (targetVariant in otherVariants) {
                    val segment = SegmentModel(
                        key = key,
                        text = targetVariant.message,
                    )
                    val lang = targetVariant.lang
                    if (registry[lang] == null) {
                        registry[lang] = mutableListOf()
                    }
                    (registry[lang] as MutableList<SegmentModel>) += segment
                }
            }

            for (lang in registry.keys) {
                val languageId = languageRepository.getByCode(lang, projectId)?.id ?: run {
                    languageRepository.create(
                        model = LanguageModel(code = lang, isBase = lang == baseLanguage),
                        projectId = projectId,
                    )
                }
                segmentRepository.createBatch(models = registry[lang] ?: emptyList(), languageId = languageId)
            }

            project.copy(id = projectId)
        }
    }
}
