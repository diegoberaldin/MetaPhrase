package com.github.diegoberaldin.metaphrase.core.localization.usecase

import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals

class XmlParseResourceUseCaseTest {
    private val sut = XmlParseResourceUseCase()
    private val mockStream = ByteArrayInputStream(
        """
<?xml version="1.0" encoding="UTF-8"?>
<resources>
	<string name="app_intro">Please open a project by selecting one of the following items.</string>
</resources>
        """.trimIndent().toByteArray(StandardCharsets.UTF_8),
    )

    @Test
    fun givenFileWithALanguageWhenParsedThenMessagesAreReturned() {
        val res = sut(inputStream = mockStream, "en")
        assertEquals(1, res.size)
        val first = res.first()
        assertEquals("app_intro", first.key)
        assertEquals("Please open a project by selecting one of the following items.", first.value)
    }
}
