package app.datasource.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "text", index = true, collate = ColumnInfo.NOCASE)
    var text: String = "",
    @ColumnInfo(name = "created")
    var created: Date = Date(),
    @ColumnInfo(name = "updated")
    var updated: Date = Date(),
    @ColumnInfo(name = "completed", index = true)
    var completed: Boolean = false
)