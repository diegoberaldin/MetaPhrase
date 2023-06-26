package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.formats.android.ExportAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.flutter.ExportArbUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.ExportIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.json.ExportJsonUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.ExportPoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.properties.ExportPropertiesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.ExportResxUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DefaultExportResourcesUseCaseTest {
    private val mockExportAndroidUseCase = mockk<ExportAndroidResourcesUseCase>()
    private val mockExportIosUseCase = mockk<ExportIosResourcesUseCase>()
    private val mockExportResxUseCase = mockk<ExportResxUseCase>()
    private val mockExportJsonUseCase = mockk<ExportJsonUseCase>()
    private val mockExportArbUseCase = mockk<ExportArbUseCase>()
    private val mockExportPoUseCase = mockk<ExportPoUseCase>()
    private val mockExportPropertiesUseCase = mockk<ExportPropertiesUseCase>()
    private val sut = DefaultExportResourcesUseCase(
        exportAndroid = mockExportAndroidUseCase,
        exportIos = mockExportIosUseCase,
        exportResx = mockExportResxUseCase,
        exportPo = mockExportPoUseCase,
        exportJson = mockExportJsonUseCase,
        exportArb = mockExportArbUseCase,
        exportProperties = mockExportPropertiesUseCase,
    )

    @Test
    fun givenUseCaseWhenInvokedAndroidThenExportFunctionIsCalled() = runTest {
        coEvery { mockExportAndroidUseCase.invoke(any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        val outputPath = "strings.xml"
        sut.invoke(
            segments = segments,
            path = outputPath,
            lang = "en",
            type = ResourceFileType.ANDROID_XML,
        )
        coVerify {
            mockExportAndroidUseCase.invoke(segments, outputPath)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedIosThenExportFunctionIsCalled() = runTest {
        coEvery { mockExportIosUseCase.invoke(any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        val outputPath = "en.strings"
        sut.invoke(
            segments = segments,
            path = outputPath,
            lang = "en",
            type = ResourceFileType.IOS_STRINGS,
        )
        coVerify {
            mockExportIosUseCase.invoke(segments, outputPath)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedResxThenExportFunctionIsCalled() = runTest {
        coEvery { mockExportResxUseCase.invoke(any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        val outputPath = "strings.resx"
        sut.invoke(
            segments = segments,
            path = outputPath,
            lang = "en",
            type = ResourceFileType.RESX,
        )
        coVerify {
            mockExportResxUseCase.invoke(segments, outputPath)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedJsonThenExportFunctionIsCalled() = runTest {
        coEvery { mockExportJsonUseCase.invoke(any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        val outputPath = "strings.json"
        sut.invoke(
            segments = segments,
            path = outputPath,
            lang = "en",
            type = ResourceFileType.JSON,
        )
        coVerify {
            mockExportJsonUseCase.invoke(segments, outputPath)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedArbThenExportFunctionIsCalled() = runTest {
        coEvery { mockExportArbUseCase.invoke(any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        val outputPath = "strings.arb"
        sut.invoke(
            segments = segments,
            path = outputPath,
            lang = "en",
            type = ResourceFileType.ARB,
        )
        coVerify {
            mockExportArbUseCase.invoke(segments, outputPath)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedPoThenExportFunctionIsCalled() = runTest {
        coEvery { mockExportPoUseCase.invoke(any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        val outputPath = "messages.po"
        sut.invoke(
            segments = segments,
            path = outputPath,
            lang = "en",
            type = ResourceFileType.PO,
        )
        coVerify {
            mockExportPoUseCase.invoke(segments, outputPath, "en")
        }
    }

    @Test
    fun givenUseCaseWhenInvokedPropertiesThenExportFunctionIsCalled() = runTest {
        coEvery { mockExportPropertiesUseCase.invoke(any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "message_key", text = "Message value", translatable = true))
        val outputPath = "strings.properties"
        sut.invoke(
            segments = segments,
            path = outputPath,
            lang = "en",
            type = ResourceFileType.PROPERTIES,
        )
        coVerify {
            mockExportPropertiesUseCase.invoke(segments, outputPath)
        }
    }
}
