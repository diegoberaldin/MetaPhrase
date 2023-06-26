package com.github.diegoberaldin.metaphrase.domain.language.persistence.dao

import com.github.diegoberaldin.metaphrase.core.persistence.AppDatabase
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DefaultLanguageDaoTest {

    private lateinit var appDb: AppDatabase
    private lateinit var sut: DefaultLanguageDao
    private var projectId: Int = 0

    @BeforeTest
    fun setup() {
        MockFileManager.setup(
            name = "test",
            extension = ".db",
        )
        appDb = AppDatabase(
            filename = "test",
            fileManager = MockFileManager,
        )
        sut = appDb.languageDao()

        val projectDao = appDb.projectDao()
        runBlocking {
            projectId = projectDao.create(ProjectModel(name = "test"))
        }
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenEmptyProjectWhenLanguageCreatedThenRowIsCreated() = runTest {
        val model = LanguageModel(code = "en")
        val id = sut.create(model = model, projectId = projectId)
        assertTrue(id > 0)
    }

    @Test
    fun givenExistingLanguageWhenGeyByIdIsCalledThenValueIsReturned() = runTest {
        val model = LanguageModel(code = "en")
        val id = sut.create(model = model, projectId = projectId)

        val res = sut.getById(id)
        assertNotNull(res)
        assertEquals("en", res.code)
    }

    @Test
    fun givenExistingLanguageWhenGeyByCodeIsCalledThenValueIsReturned() = runTest {
        val model = LanguageModel(code = "en")
        sut.create(model = model, projectId = projectId)

        val res = sut.getByCode(code = "en", projectId = projectId)
        assertNotNull(res)
        assertEquals("en", res.code)
    }

    @Test
    fun givenNonExistingLanguageWhenGeyByCodeIsCalledThenValueIsReturned() = runTest {
        val model = LanguageModel(code = "en")
        sut.create(model = model, projectId = projectId)

        val res = sut.getByCode(code = "it", projectId = projectId)
        assertNotNull(res)
    }

    @Test
    fun givenExistingLanguageWhenLanguageIsDeletedThenNoValueIsReturned() = runTest {
        val model = LanguageModel(code = "en")
        val id = sut.create(model = model, projectId = projectId)
        val old = sut.getById(id)
        assertNotNull(old)

        sut.delete(model.copy(id = id))

        val res = sut.getById(id)
        assertNull(res)
    }

    @Test
    fun givenExistingLanguagesWhenGetAllIsCalledThenAllValuesReturned() = runTest {
        val model = LanguageModel(projectId, code = "en")
        val model2 = LanguageModel(projectId, code = "it")
        sut.create(model = model, projectId = projectId)
        sut.create(model = model2, projectId = projectId)

        val res = sut.getAll(projectId = projectId)

        assertEquals(2, res.size)
    }
}
