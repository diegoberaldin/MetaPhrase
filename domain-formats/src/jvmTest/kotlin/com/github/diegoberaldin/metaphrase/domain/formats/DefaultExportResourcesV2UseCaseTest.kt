package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultExportResourcesV2UseCaseTest {

    private val mockBaseUseCase = mockk<ExportResourcesUseCase>()
    private val sut = DefaultExportResourcesV2UseCase(
        baseUseCase = mockBaseUseCase,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup(
            name = "test",
            extension = ".zip",
        )
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenUseCaseWhenInvokedForAndroidThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode = "en"
        val data = mapOf(langCode to segments)
        val resourceType = ResourceFileType.ANDROID_XML
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForAndroidMultipleThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode1 = "en"
        val langCode2 = "it"
        val data = mapOf(langCode1 to segments, langCode2 to segments)
        val resourceType = ResourceFileType.ANDROID_XML
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode1, resourceType)
            mockBaseUseCase.invoke(segments, any(), langCode2, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForIosThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode = "en"
        val data = mapOf(langCode to segments)
        val resourceType = ResourceFileType.IOS_STRINGS
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForIosMultipleThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode1 = "en"
        val langCode2 = "it"
        val data = mapOf(langCode1 to segments, langCode2 to segments)
        val resourceType = ResourceFileType.IOS_STRINGS
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode1, resourceType)
            mockBaseUseCase.invoke(segments, any(), langCode2, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForResxThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode = "en"
        val data = mapOf(langCode to segments)
        val resourceType = ResourceFileType.RESX
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForResxMultipleThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode1 = "en"
        val langCode2 = "it"
        val data = mapOf(langCode1 to segments, langCode2 to segments)
        val resourceType = ResourceFileType.RESX
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode1, resourceType)
            mockBaseUseCase.invoke(segments, any(), langCode2, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForPoThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode = "en"
        val data = mapOf(langCode to segments)
        val resourceType = ResourceFileType.PO
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForPoMultipleThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode1 = "en"
        val langCode2 = "it"
        val data = mapOf(langCode1 to segments, langCode2 to segments)
        val resourceType = ResourceFileType.PO
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode1, resourceType)
            mockBaseUseCase.invoke(segments, any(), langCode2, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForJsonThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode = "en"
        val data = mapOf(langCode to segments)
        val resourceType = ResourceFileType.JSON
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForJsonMultipleThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode1 = "en"
        val langCode2 = "it"
        val data = mapOf(langCode1 to segments, langCode2 to segments)
        val resourceType = ResourceFileType.JSON
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode1, resourceType)
            mockBaseUseCase.invoke(segments, any(), langCode2, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForArbThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode = "en"
        val data = mapOf(langCode to segments)
        val resourceType = ResourceFileType.ARB
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForArbMultipleThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode1 = "en"
        val langCode2 = "it"
        val data = mapOf(langCode1 to segments, langCode2 to segments)
        val resourceType = ResourceFileType.ARB
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode1, resourceType)
            mockBaseUseCase.invoke(segments, any(), langCode2, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForPropertiesThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode = "en"
        val data = mapOf(langCode to segments)
        val resourceType = ResourceFileType.PROPERTIES
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode, resourceType)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForPropertiesMultipleThenBaseIsCalled() = runTest {
        coEvery { mockBaseUseCase.invoke(any(), any(), any(), any()) } returns Unit
        val segments = listOf(SegmentModel(key = "key"))
        val langCode1 = "en"
        val langCode2 = "it"
        val data = mapOf(langCode1 to segments, langCode2 to segments)
        val resourceType = ResourceFileType.PROPERTIES
        sut.invoke(
            data = data,
            path = MockFileManager.getFilePath(),
            type = resourceType,
        )
        coVerify {
            mockBaseUseCase.invoke(segments, any(), langCode1, resourceType)
            mockBaseUseCase.invoke(segments, any(), langCode2, resourceType)
        }
    }
}
