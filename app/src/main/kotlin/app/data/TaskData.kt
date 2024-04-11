package app.data

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.style.TextDecoration
import app.datasource.database.room.entity.Task

@Immutable
data class TaskData(
    val task: Task,
    val maxLines: Int = 3
) {

    @Stable
    fun getText(): String {
        return task.text
    }

    @Stable
    fun getTextDecoration(): TextDecoration? {
        if (task.completed) return TextDecoration.LineThrough
        return null
    }

    @Stable
    fun isCompleted(): Boolean {
        return task.completed
    }

}