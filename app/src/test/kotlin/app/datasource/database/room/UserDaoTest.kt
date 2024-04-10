package app.datasource.database.room

import androidx.paging.PagingConfig
import androidx.paging.testing.TestPager
import app.BaseHiltUnitTest
import app.datasource.database.room.entity.User
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class UserDaoTest : BaseHiltUnitTest() {

    @Inject
    lateinit var roomSource: AppRoomSource

    @Test
    fun create() = performTest {
        val dao = roomSource.userDao
        val user = User()
        user.id = dao.create(user)
        Assert.assertNotEquals(0L, user.id)
        Assert.assertEquals(user, dao.get(user.id))
    }

    @Test
    fun getAllPaginated() = performTest {
        val dao = roomSource.userDao
        dao.create(User(firstName = "A"))
        dao.create(User(firstName = "B"))
        dao.create(User(firstName = "C"))
        dao.create(User(firstName = "D"))

        val pager = TestPager(PagingConfig(10), dao.getAllPaginated()).apply {
            refresh()
        }
        val pages = pager.getPages()
        Assert.assertEquals(1, pages.size)

        val page = pages.first()
        Assert.assertEquals(4, page.data.size)
        page.data.forEach { user -> Assert.assertNotNull(user) }
    }

}