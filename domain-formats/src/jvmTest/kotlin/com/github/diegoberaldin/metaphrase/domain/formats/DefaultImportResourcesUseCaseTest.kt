package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.formats.android.ParseAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.flutter.ParseArbUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.ParseIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.json.ParseJsonUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.ParsePoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.properties.ParsePropertiesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.ParseResxUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultImportResourcesUseCaseTest {
    private val mockParseAndroid = mockk<ParseAndroidResourcesUseCase>()
    private val mockParseIos = mockk<ParseIosResourcesUseCase>()
    private val mockParseResx = mockk<ParseResxUseCase>()
    private val mockParseJson = mockk<ParseJsonUseCase>()
    private val mockParseArb = mockk<ParseArbUseCase>()
    private val mockParsePo = mockk<ParsePoUseCase>()
    private val mockParseProperties = mockk<ParsePropertiesUseCase>()
    private val sut = DefaultImportResourcesUseCase(
        parseAndroid = mockParseAndroid,
        parseIos = mockParseIos,
        parseResx = mockParseResx,
        parsePo = mockParsePo,
        parseJson = mockParseJson,
        parseArb = mockParseArb,
        parseProperties = mockParseProperties,
    )

    @Test
    fun givenUseCaseWhenInvokedAndroidThenParseFunctionIsCalled() = runTest {
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        coEvery { mockParseAndroid.invoke(any()) } returns segments
        val inputPath = "strings.xml"
        val res = sut.invoke(path = inputPath, type = ResourceFileType.ANDROID_XML)
        assertEquals(segments, res)
        coVerify { mockParseAndroid.invoke(inputPath) }
    }

    @Test
    fun givenUseCaseWhenInvokedIosThenParseFunctionIsCalled() = runTest {
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        coEvery { mockParseIos.invoke(any()) } returns segments
        val inputPath = "en.strings"
        val res = sut.invoke(path = inputPath, type = ResourceFileType.IOS_STRINGS)
        assertEquals(segments, res)
        coVerify { mockParseIos.invoke(inputPath) }
    }

    @Test
    fun givenUseCaseWhenInvokedResxThenParseFunctionIsCalled() = runTest {
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        coEvery { mockParseResx.invoke(any()) } returns segments
        val inputPath = "strings.resx"
        val res = sut.invoke(path = inputPath, type = ResourceFileType.RESX)
        assertEquals(segments, res)
        coVerify { mockParseResx.invoke(inputPath) }
    }

    @Test
    fun givenUseCaseWhenInvokedPoThenParseFunctionIsCalled() = runTest {
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        coEvery { mockParsePo.invoke(any()) } returns segments
        val inputPath = "messages.po"
        val res = sut.invoke(path = inputPath, type = ResourceFileType.PO)
        assertEquals(segments, res)
        coVerify { mockParsePo.invoke(inputPath) }
    }

    @Test
    fun givenUseCaseWhenInvokedJsonThenParseFunctionIsCalled() = runTest {
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        coEvery { mockParseJson.invoke(any()) } returns segments
        val inputPath = "strings.json"
        val res = sut.invoke(path = inputPath, type = ResourceFileType.JSON)
        assertEquals(segments, res)
        coVerify { mockParseJson.invoke(inputPath) }
    }

    @Test
    fun givenUseCaseWhenInvokedArbThenParseFunctionIsCalled() = runTest {
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        coEvery { mockParseArb.invoke(any()) } returns segments
        val inputPath = "strings.arb"
        val res = sut.invoke(path = inputPath, type = ResourceFileType.ARB)
        assertEquals(segments, res)
        coVerify { mockParseArb.invoke(inputPath) }
    }

    @Test
    fun givenUseCaseWhenInvokedPropertiesThenParseFunctionIsCalled() = runTest {
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        coEvery { mockParseProperties.invoke(any()) } returns segments
        val inputPath = "strings.properties"
        val res = sut.invoke(path = inputPath, type = ResourceFileType.PROPERTIES)
        assertEquals(segments, res)
        coVerify { mockParseProperties.invoke(inputPath) }
    }
}
