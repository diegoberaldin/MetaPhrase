package com.github.diegoberaldin.metaphrase.core.persistence

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao.GlossaryTermDao
import com.github.diegoberaldin.metaphrase.domain.language.persistence.dao.LanguageDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.ProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.RecentProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.SegmentDao
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao.MemoryEntryDao
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class DefaultAppDatabaseTest {

    private lateinit var sut: AppDatabase

    @BeforeTest
    fun setup() {
        MockFileManager.setup(
            name = "test",
            extension = ".db",
        )
        sut = DefaultAppDatabase(
            filename = "test",
            fileManager = MockFileManager,
        )
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenDbWhenInvokeProjectDaoThenReturnsValidObject() {
        val res = sut.projectDao()
        assertIs<ProjectDao>(res)
    }

    @Test
    fun givenDbWhenInvokeLanguageDaoThenReturnsValidObject() {
        val res = sut.languageDao()
        assertIs<LanguageDao>(res)
    }

    @Test
    fun givenDbWhenInvokeSegmentDaoThenReturnsValidObject() {
        val res = sut.segmentDao()
        assertIs<SegmentDao>(res)
    }

    @Test
    fun givenDbWhenInvokeMemoryEntryDaoThenReturnsValidObject() {
        val res = sut.memoryEntryDao()
        assertIs<MemoryEntryDao>(res)
    }

    @Test
    fun givenDbWhenInvokeGlossaryTermDaoThenReturnsValidObject() {
        val res = sut.glossaryTermDao()
        assertIs<GlossaryTermDao>(res)
    }

    @Test
    fun givenDbWhenInvokeRecentProjectDaoThenReturnsValidObject() {
        val res = sut.recentProjectDao()
        assertIs<RecentProjectDao>(res)
    }
}
