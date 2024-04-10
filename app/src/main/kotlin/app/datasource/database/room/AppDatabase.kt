package app.datasource.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.datasource.database.room.dao.UserDao
import app.datasource.database.room.entity.User

/**
 * This class represents the Room database for the application.
 */
@Database(
    entities = [
        User::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Retrieves the DAO (Data Access Object) for interacting with the User entity.
     *
     * @return The UserDao instance.
     */
    abstract fun getUserDao(): UserDao

}