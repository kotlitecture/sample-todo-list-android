package app.datasource.database.room

import androidx.room.TypeConverter
import java.util.Date

/**
 * Class containing Room type converters for Date objects.
 */
class Converters {

    /**
     * Converts a timestamp value (Long) to a Date object.
     *
     * @param value The timestamp value to convert.
     * @return The corresponding Date object, or null if the input value is null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Converts a Date object to a timestamp value (Long).
     *
     * @param date The Date object to convert.
     * @return The corresponding timestamp value, or null if the input date is null.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}