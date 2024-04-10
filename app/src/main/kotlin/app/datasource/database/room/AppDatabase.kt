package app.datasource.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.datasource.database.room.dao.TaskDao
import app.datasource.database.room.entity.Task

/**
 * This class represents the Room database for the application.
 */
@Database(
    entities = [
        Task::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

}