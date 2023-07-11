package com.github.diegoberaldin.metaphrase.core.localization.usecase

import com.github.diegoberaldin.metaphrase.core.localization.data.LocalizableString
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import java.io.InputStream

class TmxParseResourceUseCase : ParseResourceUseCase {
    companion object {
        private const val ELEM_BODY = "body"
        private const val ELEM_UNIT = "tu"
        private const val ELEM_VARIANT = "tuv"
        private const val ELEM_SEGMENT = "seg"
        private const val ATTR_LANGUAGE = "xml:lang"
        private const val ATTR_UNIT_ID = "tuid"
    }

    override fun invoke(inputStream: InputStream, lang: String): List<LocalizableString> {
        return runCatching {
            val rootNode = parse(inputStream)
            val res = mutableListOf<LocalizableString>()

            val bodyNode = rootNode.children.firstOrNull { (it as? Node)?.nodeName == ELEM_BODY } as? Node
            if (bodyNode != null) {
                for (unit in bodyNode.children.filter { (it as? Node)?.nodeName == ELEM_UNIT }) {
                    val key = ((unit as Node).attributes[ATTR_UNIT_ID] as? String).orEmpty()
                    val variant =
                        unit.children.firstOrNull {
                            (it as? Node)?.nodeName == ELEM_VARIANT && (it.attributes[ATTR_LANGUAGE] as? String) == lang
                        } as? Node
                    val segment = variant?.children?.firstOrNull {
                        (it as? Node)?.nodeName == ELEM_SEGMENT
                    } as? Node
                    val text = (segment?.children?.firstOrNull() as? TextElement)?.text
                    if (text != null) {
                        res += LocalizableString(
                            key = key,
                            value = text,
                        )
                    }
                }
            }

            res
        }.also {
            it.exceptionOrNull()?.also { exc ->
                exc.printStackTrace()
            }
        }.getOrElse { emptyList() }
    }
}
