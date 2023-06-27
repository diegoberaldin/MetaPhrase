package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DefaultValidatePlaceholderUseCaseTest {
    private val sut = DefaultValidatePlaceholdersUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @Test
    fun givenUseCaseWhenInvokeOnCorrectSegmentsThenResultIsValid() = runTest {
        val res = sut.invoke(
            pairs = listOf(
                SegmentModel(key = "key", text = "test") to SegmentModel(key = "key", text = "test"),
            ),
        )
        assertIs<ValidatePlaceholdersUseCase.Output.Valid>(res)
    }

    @Test
    fun givenUseCaseWhenInvokeOnSegmentsWithMissingThenResultIsValid() = runTest {
        val res = sut.invoke(
            pairs = listOf(
                SegmentModel(key = "key", text = "test %d") to SegmentModel(key = "key", text = "test"),
            ),
        )
        assertIs<ValidatePlaceholdersUseCase.Output.Invalid>(res)
        assertEquals(1, res.keys.size)
        assertEquals("key", res.keys.first())
    }

    @Test
    fun givenUseCaseWhenInvokeOnSegmentsWithExceedingThenResultIsValid() = runTest {
        val res = sut.invoke(
            pairs = listOf(
                SegmentModel(key = "key", text = "test") to SegmentModel(key = "key", text = "test %d"),
            ),
        )
        assertIs<ValidatePlaceholdersUseCase.Output.Invalid>(res)
        assertEquals(1, res.keys.size)
        assertEquals("key", res.keys.first())
    }

    @Test
    fun givenUseCaseWhenInvokeOnSegmentsWithMissingAndExceedingThenResultIsValid() = runTest {
        val res = sut.invoke(
            pairs = listOf(
                SegmentModel(key = "key", text = "test %s") to SegmentModel(key = "key", text = "test %d"),
            ),
        )
        assertIs<ValidatePlaceholdersUseCase.Output.Invalid>(res)
        assertEquals(1, res.keys.size)
        assertEquals("key", res.keys.first())
    }
}
