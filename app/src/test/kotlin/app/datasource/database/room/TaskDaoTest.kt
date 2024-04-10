package app.datasource.database.room

import androidx.paging.PagingConfig
import androidx.paging.testing.TestPager
import app.BaseHiltUnitTest
import app.datasource.database.room.entity.Task
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TaskDaoTest : BaseHiltUnitTest() {

    @Inject
    lateinit var roomSource: AppRoomSource

    @Test
    fun `created note exists`() = performTest {
        val dao = roomSource.taskDao
        val note = Task()
        note.id = dao.create(note)
        Assert.assertNotEquals(0L, note.id)
        Assert.assertEquals(note, dao.get(note.id))
    }

    @Test
    fun `created note can be found by text`() = performTest {
        val dao = roomSource.taskDao
        dao.create(
            Task(text = "AAA"),
            Task(text = "aAa"),
            Task(text = "BBB"),
            Task(text = "AbbA"),
        )

        val pager = TestPager(PagingConfig(10), dao.getAllPaginated("a")).apply { refresh() }
        val pages = pager.getPages()
        Assert.assertEquals(1, pages.size)

        val notes = pages.first().data
        Assert.assertEquals(3, notes.size)
        Assert.assertTrue(notes.all { it.text.contains("a", ignoreCase = true) })
    }

}