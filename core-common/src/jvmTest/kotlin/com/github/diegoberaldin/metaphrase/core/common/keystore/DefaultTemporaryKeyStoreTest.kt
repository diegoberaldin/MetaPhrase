package com.github.diegoberaldin.metaphrase.core.common.keystore

import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class DefaultTemporaryKeyStoreTest {

    private var sut = DefaultTemporaryKeyStore(fileManager = MockFileManager)

    @BeforeTest
    fun setup() {
        MockFileManager.setup(
            name = "test",
            extension = ".preferences_pb",
        )
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenEmptyStoreWhenQueriedIntThenDefaultValueIsReturned() = runTest {
        val key = "intKey"
        val retrieved = sut.get(key, 0)
        assertEquals(0, retrieved)
    }

    @Test
    fun givenSavedIntWhenQueriedWithSameKeyThenCorrectValueIsReturned() = runTest {
        val value = 42
        val key = "intKey"
        sut.save(key, value)

        val retrieved = sut.get(key, 0)
        assertEquals(value, retrieved)
    }

    @Test
    fun givenSavedIntWhenQueriedWithDifferentKeyThenDefaultValueIsReturned() = runTest {
        val value = 42
        val key = "intKey"
        val otherKey = "intKey2"
        sut.save(key, value)

        val retrieved = sut.get(otherKey, 0)
        assertEquals(0, retrieved)
    }

    @Test
    fun givenEmptyStoreWhenQueriedFloatThenDefaultValueIsReturned() = runTest {
        val key = "intKey"
        val retrieved = sut.get(key, 0f)
        assertEquals(0f, retrieved)
    }

    @Test
    fun givenSavedFloatWhenQueriedWithSameKeyThenCorrectValueIsReturned() = runTest {
        val value = 42f
        val key = "floatKey"
        sut.save(key, value)

        val retrieved = sut.get(key, 0f)
        assertEquals(value, retrieved)
    }

    @Test
    fun givenSavedFloatWhenQueriedWithDifferentKeyThenDefaultValueIsReturned() = runTest {
        val value = 42f
        val key = "floatKey"
        val otherKey = "floatKey2"
        sut.save(key, value)

        val retrieved = sut.get(otherKey, 0f)
        assertEquals(0f, retrieved)
    }

    @Test
    fun givenEmptyStoreWhenQueriedDoubleThenDefaultValueIsReturned() = runTest {
        val key = "doubleKey"
        val retrieved = sut.get(key, 0.0)
        assertEquals(0.0, retrieved)
    }

    @Test
    fun givenSavedDoubleWhenQueriedWithSameKeyThenCorrectValueIsReturned() = runTest {
        val value = 42.0
        val key = "doubleKey"
        sut.save(key, value)

        val retrieved = sut.get(key, 0.0)
        assertEquals(value, retrieved)
    }

    @Test
    fun givenSavedDoubleWhenQueriedWithDifferentKeyThenDefaultValueIsReturned() = runTest {
        val value = 42.0
        val key = "doubleKey"
        val otherKey = "doubleKey2"
        sut.save(key, value)

        val retrieved = sut.get(otherKey, 0.0)
        assertEquals(0.0, retrieved)
    }

    @Test
    fun givenEmptyStoreWhenQueriedBoolThenDefaultValueIsReturned() = runTest {
        val key = "boolKey"
        val retrieved = sut.get(key, false)
        assertFalse(retrieved)
    }

    @Test
    fun givenSavedBoolWhenQueriedWithSameKeyThenCorrectValueIsReturned() = runTest {
        val value = true
        val key = "boolKey"
        sut.save(key, value)

        val retrieved = sut.get(key, false)
        assertEquals(value, retrieved)
    }

    @Test
    fun givenSavedBoolWhenQueriedWithDifferentKeyThenDefaultValueIsReturned() = runTest {
        val value = true
        val key = "boolKey"
        val otherKey = "boolKey2"
        sut.save(key, value)

        val retrieved = sut.get(otherKey, false)
        assertFalse(retrieved)
    }

    @Test
    fun givenEmptyStoreWhenQueriedStringThenDefaultValueIsReturned() = runTest {
        val key = "stringKey"
        val retrieved = sut.get(key, "")
        assertEquals("", retrieved)
    }

    @Test
    fun givenSavedStoreWhenQueriedWithSameKeyThenCorrectValueIsReturned() = runTest {
        val value = "value"
        val key = "stringKey"
        sut.save(key, value)

        val retrieved = sut.get(key, "")
        assertEquals(value, retrieved)
    }

    @Test
    fun givenSavedStringWhenQueriedWithDifferentKeyThenDefaultValueIsReturned() = runTest {
        val value = "value"
        val key = "stringKey"
        val otherKey = "stringKey2"
        sut.save(key, value)

        val retrieved = sut.get(otherKey, "")
        assertEquals("", retrieved)
    }
}
