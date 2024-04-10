package app.datasource.database.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.datasource.database.room.entity.Task

@Dao
interface TaskDao {

    @Insert
    fun create(note: Task): Long

    @Insert
    fun create(vararg notes: Task)

    @Update
    fun update(vararg notes: Task)

    @Delete
    fun delete(vararg notes: Task)

    @Query("SELECT * FROM task WHERE id = :id LIMIT 1")
    fun get(id: Long): Task?

    @Query("SELECT * FROM task WHERE completed = :completed ORDER BY updated DESC")
    fun getAllPaginated(completed: Boolean): PagingSource<Int, Task>

    @Query("SELECT * FROM task WHERE text LIKE '%' || :query || '%' ORDER BY updated DESC")
    fun getAllPaginated(query: String): PagingSource<Int, Task>

    @Query("SELECT * FROM task ORDER BY updated DESC")
    fun getAllPaginated(): PagingSource<Int, Task>

}