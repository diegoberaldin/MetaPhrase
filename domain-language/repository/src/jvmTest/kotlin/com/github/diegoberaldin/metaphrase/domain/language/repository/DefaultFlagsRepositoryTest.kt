package com.github.diegoberaldin.metaphrase.domain.language.repository

import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultFlagsRepositoryTest {

    private val sut = DefaultFlagsRepository()

    @Test
    fun givenRepositoryWhenItLanguageIsQueriedThenCorrectFlagIsReturned() {
        val res = sut.getFlag("it")
        assertEquals("\uD83C\uDDEE\uD83C\uDDF9", res)
    }

    @Test
    fun givenRepositoryWhenDeLanguageIsQueriedThenCorrectFlagIsReturned() {
        val res = sut.getFlag("de")
        assertEquals("\uD83C\uDDE9\uD83C\uDDEA", res)
    }

    @Test
    fun givenRepositoryWhenFrLanguageIsQueriedThenCorrectFlagIsReturned() {
        val res = sut.getFlag("fr")
        assertEquals("\uD83C\uDDEB\uD83C\uDDF7", res)
    }

    @Test
    fun givenRepositoryWhenElLanguageIsQueriedThenCorrectFlagIsReturned() {
        val res = sut.getFlag("el")
        assertEquals("\uD83C\uDDEC\uD83C\uDDF7", res)
    }

    @Test
    fun givenRepositoryWhenUnknownLanguageIsQueriedThenDefaultFlagIsReturned() {
        val res = sut.getFlag("xx")
        assertEquals("\uD83C\uDDEC\uD83C\uDDE7", res)
    }
}
