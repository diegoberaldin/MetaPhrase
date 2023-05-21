package repository.usecase

import data.SegmentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileReader
import javax.xml.namespace.QName
import javax.xml.stream.XMLInputFactory

class ParseAndroidResourcesUseCase {

    companion object {
        private const val ELEM_STRING = "string"
        private const val ATTR_NAME = "name"
        private const val ATTR_TRANSLATABLE = "translatable"
    }

    suspend operator fun invoke(path: String): List<SegmentModel> {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return emptyList()
        }
        return withContext(Dispatchers.IO) {
            runCatching {
                val res = mutableListOf<SegmentModel>()

                FileReader(file).use { reader ->
                    val inputFactory = XMLInputFactory.newInstance()
                    val eventReader = inputFactory.createXMLEventReader(reader)

                    while (eventReader.hasNext()) {
                        val evt = eventReader.nextEvent()
                        if (evt.isStartElement) {
                            val startElement = evt.asStartElement()
                            val elemName = startElement.name.localPart
                            if (elemName == ELEM_STRING) {
                                val key = runCatching {
                                    startElement.getAttributeByName(QName(ATTR_NAME)).value
                                }.getOrElse { "" }
                                val translatable = runCatching {
                                    startElement.getAttributeByName(QName(ATTR_TRANSLATABLE)).value.toBoolean()
                                }.getOrElse { true }
                                val text = runCatching {
                                    eventReader.nextEvent().asCharacters().data
                                }.getOrElse { "" }.sanitize()
                                val segment = SegmentModel(
                                    key = key,
                                    text = text,
                                    translatable = translatable,
                                )
                                res += segment
                            }
                        }
                    }
                }
                res
            }.getOrElse { emptyList() }
        }
    }

    private fun String.sanitize(): String {
        val substitutionList = listOf(
            "\\'" to "'",
        )
        return substitutionList.fold(this) { res, it ->
            res.replace(it.first, it.second)
        }
    }
}
