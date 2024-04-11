package app.repository

import androidx.paging.PagingData
import androidx.paging.map
import app.data.TaskData
import app.datasource.database.room.AppRoomSource
import app.datasource.database.room.entity.Task
import app.datasource.paging.AppPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val pagingSource: AppPagingSource,
    private val roomSource: AppRoomSource,
) {

    fun create(text: String) {
        roomSource.taskDao.create(Task(text = text))
    }

    fun update(data: TaskData) {
        val task = data.task
        task.updated = Date()
        roomSource.taskDao.update(task)
    }

    fun getAll(completed: Boolean): Flow<PagingData<TaskData>> {
        return pagingSource.getPager { roomSource.taskDao.getAllPaginated(completed) }
            .flow.map { page -> page.map { TaskData(it) } }
    }

    fun findAll(query: String?): Flow<PagingData<TaskData>> {
        return pagingSource
            .getPager {
                val dao = roomSource.taskDao
                query?.let { dao.getAllPaginated(query) } ?: dao.getAllPaginated()
            }
            .flow.map { page -> page.map { TaskData(it) } }
    }

}