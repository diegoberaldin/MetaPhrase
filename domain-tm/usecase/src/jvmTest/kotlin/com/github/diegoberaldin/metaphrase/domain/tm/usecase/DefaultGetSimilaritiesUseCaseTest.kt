package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource.MemoryTranslationUnitSource
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource.ProjectTranslationUnitSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultGetSimilaritiesUseCaseTest {
    private val mockProjectSource = mockk<ProjectTranslationUnitSource>()
    private val mockTmSource = mockk<MemoryTranslationUnitSource>()
    private val sut = DefaultGetSimilaritiesUseCase(
        projectSource = mockProjectSource,
        memorySource = mockTmSource,
    )

    @Test
    fun givenUseCaseWithWhenInvokedThenUnitsFromTwoSourcesAreMerged() = runTest {
        val sourceSegment = SegmentModel(key = "key", text = "test")
        val targetSegment = SegmentModel(key = "key", text = "prova")
        coEvery { mockProjectSource.getUnits(any(), any(), any(), any()) } returns listOf(
            TranslationUnit(
                segment = targetSegment,
                original = sourceSegment,
                origin = "this project",
            ),
        )
        coEvery { mockTmSource.getUnits(any(), any(), any(), any()) } returns listOf(
            TranslationUnit(
                segment = targetSegment,
                original = sourceSegment.copy(text = "testing"),
                origin = "memory",
            ),
        )
        val res = sut.invoke(
            segment = sourceSegment,
            projectId = 0,
            languageId = 1,
        )

        assertEquals(2, res.size)
        assertTrue(res.any { it.origin == "this project" })
        assertTrue(res.any { it.origin == "memory" })
    }

    @Test
    fun givenUseCaseWithWhenInvokedThenUnitsFromTwoSourcesAreMergedAndSameSourceMessageAreCollapsed() = runTest {
        val sourceSegment = SegmentModel(key = "key", text = "test")
        val targetSegment = SegmentModel(key = "key", text = "prova")
        coEvery { mockProjectSource.getUnits(any(), any(), any(), any()) } returns listOf(
            TranslationUnit(
                segment = targetSegment,
                original = sourceSegment,
                origin = "this project",
            ),
        )
        coEvery { mockTmSource.getUnits(any(), any(), any(), any()) } returns listOf(
            TranslationUnit(
                segment = targetSegment,
                original = sourceSegment,
                origin = "memory",
            ),
        )
        val res = sut.invoke(
            segment = sourceSegment,
            projectId = 0,
            languageId = 1,
        )

        assertEquals(1, res.size)
        assertTrue(res.any { it.origin == "memory" })
        assertTrue(res.none { it.origin == "this project" })
    }
}
