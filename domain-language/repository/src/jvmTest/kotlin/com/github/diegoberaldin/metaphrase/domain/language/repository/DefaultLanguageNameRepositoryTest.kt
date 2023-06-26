package com.github.diegoberaldin.metaphrase.domain.language.repository

import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultLanguageNameRepositoryTest : KoinComponent {

    private val sut = DefaultLanguageNameRepository()

    companion object {
        val setup by lazy {
            startKoin { modules(localizationModule) }
            L10n.setLanguage("en")
        }
    }

    init {
        setup
    }

    @Test
    fun givenRepositoryWhenItLanguageIsQueriedThenCorrectNameIsReturned() {
        val res = sut.getName("it")
        assertEquals("Italiano", res)
    }

    @Test
    fun givenRepositoryWhenDeLanguageIsQueriedThenCorrectNameIsReturned() {
        val res = sut.getName("de")
        assertEquals("Deutsch", res)
    }

    @Test
    fun givenRepositoryWhenFrLanguageIsQueriedThenCorrectNameIsReturned() {
        val res = sut.getName("fr")
        assertEquals("Français", res)
    }

    @Test
    fun givenRepositoryWhenElLanguageIsQueriedThenCorrectNameIsReturned() {
        val res = sut.getName("el")
        assertEquals("Ελληνικά", res)
    }

    @Test
    fun givenRepositoryWhenUnknownLanguageIsQueriedThenDefaultIsReturned() {
        val res = sut.getName("xx")
        assertEquals("English", res)
    }
}
