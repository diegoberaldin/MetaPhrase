package com.github.diegoberaldin.metaphrase.domain.language.repository

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.persistence.dao.LanguageDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultLanguageRepositoryTest {
    private val dao = mockk<LanguageDao>()
    private val sut = DefaultLanguageRepository(dao)

    @Test
    fun givenRepositoryWhenCreateInvokedThenItemIsCreated() = runTest {
        val languageId = 2
        coEvery {
            dao.create(any(), any())
        } returns languageId
        val projectId = 1
        val res = sut.create(LanguageModel(code = "en"), projectId)
        assertEquals(languageId, res)
        coVerify {
            dao.create(any(), projectId)
        }
    }
}
