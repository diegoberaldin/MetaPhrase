package com.github.diegoberaldin.metaphrase.core.localization.usecase

import com.github.diegoberaldin.metaphrase.core.localization.data.LocalizableString
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import java.io.InputStream

internal class DefaultParseXmlResourceUseCase : ParseXmlResourceUseCase {

    companion object {
        private const val ELEM_STRING = "string"
        private const val ATTR_NAME = "name"
    }

    override operator fun invoke(inputStream: InputStream): List<LocalizableString> {
        return runCatching {
            val res = mutableListOf<LocalizableString>()
            val resourcesNode = parse(inputStream)
            for (elem in resourcesNode.children) {
                if ((elem as? Node)?.nodeName != ELEM_STRING) continue
                val key = (elem.attributes[ATTR_NAME] as? String).orEmpty()
                val text = (elem.children.firstOrNull() as? TextElement)?.text.orEmpty().sanitize()
                val segment = LocalizableString(
                    key = key,
                    value = text,
                )
                res += segment
            }
            res
        }.getOrElse { emptyList() }
    }

    private fun String.sanitize(): String {
        val substitutionList = listOf(
            "\\'" to "'",
            "\\n" to "\n",
        )
        return substitutionList.fold(this) { res, it ->
            res.replace(it.first, it.second)
        }
    }
}
