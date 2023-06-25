package com.github.diegoberaldin.metaphrase.core.localization.repository

import com.github.diegoberaldin.metaphrase.core.localization.data.LocalizableString
import com.github.diegoberaldin.metaphrase.core.localization.usecase.ParseResourceUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultLocalizationTest {
    private val mockParseResource = mockk<ParseResourceUseCase>() {
        coEvery {
            invoke(inputStream = any(), lang = "en")
        } returns listOf(LocalizableString("default_key", "default_value"))
    }
    private val sut = DefaultLocalization(
        parseResource = mockParseResource,
    )

    @Test
    fun givenResourcesLoadedWhenQueriedForNonExistingKeyThenValueIsEqualToTheKey() {
        coEvery {
            mockParseResource.invoke(any(), "it")
        } returns listOf(LocalizableString("key", "value"))

        sut.setLanguage("it")
        val value = sut.get("a_key")
        assertEquals("a_key", value)
    }

    @Test
    fun givenResourcesLoadedWhenQueriedForExistingKeyThenLocalizedValueIsReturned() {
        coEvery {
            mockParseResource.invoke(any(), "it")
        } returns listOf(LocalizableString("key", "value"))

        sut.setLanguage("it")
        val value = sut.get("key")
        assertEquals("value", value)
    }

    @Test
    fun givenResourcesLoadedWhenQueriedForNonExistingKeyThenDefaultValueIsReturned() {
        coEvery {
            mockParseResource.invoke(any(), "it")
        } returns listOf(LocalizableString("key", "value"))

        sut.setLanguage("it")
        val value = sut.get("default_key")
        assertEquals("default_value", value)
    }

    @Test
    fun givenResourcesLoadedWhenQueriedForBothDefaultAndLocalKeyThenLocalValueIsReturned() {
        coEvery {
            mockParseResource.invoke(any(), "it")
        } returns listOf(LocalizableString("default_key", "it_value"))

        sut.setLanguage("it")
        val value = sut.get("default_key")
        assertEquals("it_value", value)
    }
}
