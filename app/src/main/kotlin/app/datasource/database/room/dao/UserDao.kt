package app.datasource.database.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.datasource.database.room.entity.User

/**
 * Represents a DAO (Data Access Object) for interacting with the [User] domain.
 *
 * This interface serves as an example of a typical DAO.
 *
 * For more information on the anatomy of a DAO, refer to:
 * [https://developer.android.com/training/data-storage/room/accessing-data#kotlin]
 */
@Dao
interface UserDao {

    @Insert
    fun create(user: User): Long

    @Update
    fun update(vararg users: User)

    @Delete
    fun delete(vararg user: User)

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun get(id: Long): User?

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user")
    fun getAllPaginated(): PagingSource<Int, User>

    @Query("SELECT * FROM user WHERE first_name LIKE '%' || :query || '%' OR last_name LIKE '%' || :query || '%'")
    fun getAllPaginated(query: String): PagingSource<Int, User>

}