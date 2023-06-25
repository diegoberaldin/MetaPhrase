package com.github.diegoberaldin.metaphrase.domain.language.persistence.dao

import com.github.diegoberaldin.metaphrase.core.persistence.AppDatabase
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultLanguageDAOTest {

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
    fun givenEmptyTermbaseWhenLanguageCreatedThenRowIsCreated() = runTest {
        val model = LanguageModel(code = "en")
        val id = sut.create(model = model, projectId = projectId)
        assert(id > 0)
    }

    @Test
    fun givenExistingLanguageWhenGeyByIdIsCalledThenValueIsReturned() = runTest {
        val model = LanguageModel(code = "en")
        val id = sut.create(model = model, projectId = projectId)

        val res = sut.getById(id)
        assert(res != null)
        assert(res?.code == "en")
    }

    @Test
    fun givenExistingLanguageWhenGeyByCodeIsCalledThenValueIsReturned() = runTest {
        val model = LanguageModel(code = "en")
        sut.create(model = model, projectId = projectId)

        val res = sut.getByCode(code = "en", projectId = projectId)
        assert(res != null)
        assert(res?.code == "en")
    }

    @Test
    fun givenNonExistingLanguageWhenGeyByCodeIsCalledThenValueIsReturned() = runTest {
        val model = LanguageModel(code = "en")
        sut.create(model = model, projectId = projectId)

        val res = sut.getByCode(code = "it", projectId = projectId)
        assert(res == null)
    }

    @Test
    fun givenExistingLanguageWhenLanguageIsDeletedThenNoValueIsReturned() = runTest {
        val model = LanguageModel(code = "en")
        val id = sut.create(model = model, projectId = projectId)
        val old = sut.getById(id)
        assert(old != null)

        sut.delete(model.copy(id = id))

        val res = sut.getById(id)
        assert(res == null)
    }

    @Test
    fun givenExistingLanguagesWhenGetAllIsCalledThenAllValuesReturned() = runTest {
        val model = LanguageModel(projectId, code = "en")
        val model2 = LanguageModel(projectId, code = "it")
        sut.create(model = model, projectId = projectId)
        sut.create(model = model2, projectId = projectId)

        val res = sut.getAll(projectId = projectId)

        assert(res.size == 2)
    }
}
