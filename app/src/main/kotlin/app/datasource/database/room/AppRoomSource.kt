package app.datasource.database.room

import android.app.Application
import androidx.room.Room
import androidx.room.withTransaction

/**
 * This class represents a source for accessing the Room database.
 *
 * It provides access to all underlying DAO objects as well.
 *
 * @property app The application instance.
 * @property databaseName The name of the ObjectBox database.
 */
class AppRoomSource(
    private val app: Application,
    private val databaseName: String = "db"
) {

    private val db by lazy {
        Room
            .databaseBuilder(
                klass = AppDatabase::class.java,
                name = databaseName,
                context = app,
            )
            .build()
    }

    val taskDao by lazy { db.getTaskDao() }

    /**
     * Executes a transaction on the database.
     *
     * @param <R> The type of the result.
     * @param block The block of code to execute within the transaction.
     * @return The result of the transaction.
     */
    suspend fun <R> withTransaction(block: suspend () -> R): R = db.withTransaction(block)

}