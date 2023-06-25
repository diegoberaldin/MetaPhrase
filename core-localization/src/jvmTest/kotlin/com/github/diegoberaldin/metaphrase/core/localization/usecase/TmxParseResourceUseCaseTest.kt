package com.github.diegoberaldin.metaphrase.core.localization.usecase

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import kotlin.test.Test
import kotlin.test.assertEquals

class TmxParseResourceUseCaseTest {

    private val sut = TmxParseResourceUseCase()
    private val mockStream = ByteArrayInputStream(
        """
<?xml version="1.0" encoding="UTF-8"?>
<tmx version="1.4">
	<header creationTool="MetaPhrase" creationToolVersion="1.0.0" segtype="sentence" o-tmf="tmx" adminLang="en-US" srcLang="en" datatype="plaintext"/>
	<body>
		<tu tuid="app_intro">
			<tuv xml:lang="en">
				<seg>
					Please open a project by selecting one of the following items.
				</seg>
			</tuv>
			<tuv xml:lang="it">
				<seg>
					Apri un progetto selezionando una delle seguenti voci.
				</seg>
			</tuv>
        </tu>
    </body>
 </tmx>
        """.trimIndent().toByteArray(StandardCharsets.UTF_8),
    )

    @Test
    fun givenFileWithSomeLanguagesWhenParsedEnglishThenOnlyRelevantMessagesAreReturned() {
        val res = sut(inputStream = mockStream, lang = "en")
        assertEquals(1, res.size)
        val first = res.first()
        assertEquals("app_intro", first.key)
        assertEquals("Please open a project by selecting one of the following items.", first.value)
    }

    @Test
    fun givenFileWithSomeLanguagesWhenParsedItalianThenOnlyRelevantMessagesAreReturned() {
        val res = sut(inputStream = mockStream, lang = "it")
        assertEquals(1, res.size)
        val first = res.first()
        assertEquals("app_intro", first.key)
        assertEquals("Apri un progetto selezionando una delle seguenti voci.", first.value)
    }

    @Test
    fun givenFileWithSomeLanguagesWhenParsedNonExistingLanguageThenNoMessagesAreReturned() {
        val res = sut(inputStream = mockStream, lang = "es")
        assertEquals(0, res.size)
    }
}
