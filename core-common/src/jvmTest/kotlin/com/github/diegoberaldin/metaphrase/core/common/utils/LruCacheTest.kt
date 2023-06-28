package com.github.diegoberaldin.metaphrase.core.common.utils

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LruCacheTest {
    companion object {
        private const val SIZE = 10
    }

    private lateinit var sut: LruCache<String>

    @BeforeTest
    fun setup() {
        sut = LruCache(SIZE)
    }

    @Test
    fun givenCacheWhenQueriedNonExistingKeyThenNullIsRetrieved() {
        val key = "key"
        assertNull(sut[key])
    }

    @Test
    fun givenCacheWhenValueInsertedWithKeyThenSameIsValueRetrievedWithSameKey() {
        val key = "key"
        val value = "test"
        sut[key] = value
        assertEquals(value, sut[key])
    }

    @Test
    fun givenCacheWhenMultipleValuesInsertedWithKeyThenLastValueIsRetrieved() {
        val key = "key"
        val value = "test"
        val value2 = "test2"
        sut[key] = value
        sut[key] = value2
        assertEquals(value2, sut[key])
    }

    @Test
    fun givenCapacityReachedWhenQueriedForRemovedValueThenNullIsRetrieved() {
        for (i in 0 until SIZE) {
            sut["key$i"] = "value"
        }
        sut["another"] = "value"
        assertEquals("value", sut["another"])
        assertNull(sut["key0"])
    }
}
