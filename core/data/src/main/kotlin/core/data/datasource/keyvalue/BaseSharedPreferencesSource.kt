package core.data.datasource.keyvalue

import android.content.SharedPreferences
import core.data.serialization.SerializationStrategy
import java.util.concurrent.ConcurrentHashMap

/**
 * Abstract class providing a base implementation for key-value data sources.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseSharedPreferencesSource : KeyValueSource {

    private val cache = ConcurrentHashMap<String, Any?>()
    private val prefs by lazy { createSharedPreferences() }

    override suspend fun <T> save(key: String, value: T, serializationStrategy: SerializationStrategy<T>) {
        val editor = prefs.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            is Double -> editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
            else -> editor.putString(key, serializationStrategy.toString(value))
        }
        cache[key] = value
        editor.commit()
    }

    override suspend fun <T> read(key: String, serializationStrategy: SerializationStrategy<T>): T? {
        if (cache.contains(key)) {
            return cache[key] as T?
        }
        if (!prefs.contains(key)) {
            return null
        }
        val value: Any? = when (serializationStrategy.getType()) {
            java.lang.String::class.java,
            String::class.java -> prefs.getString(key, null)

            java.lang.Boolean::class.java,
            Boolean::class.java -> prefs.getBoolean(key, false)

            java.lang.Integer::class.java,
            Int::class.java -> prefs.getInt(key, 0)

            java.lang.Long::class.java,
            Long::class.java -> prefs.getLong(key, 0)

            java.lang.Float::class.java,
            Float::class.java -> prefs.getFloat(key, 0f)

            java.lang.Double::class.java,
            Double::class.java -> java.lang.Double.longBitsToDouble(prefs.getLong(key, 0))

            else -> prefs.getString(key, null)?.let(serializationStrategy::toObject)
        }
        if (value != null) {
            cache[key] = value
        }
        return value as? T
    }

    override suspend fun <T> remove(key: String, serializationStrategy: SerializationStrategy<T>): T? {
        cache.remove(key)
        val prev = read(key, serializationStrategy)
        prefs.edit().remove(key).commit()
        return prev
    }

    override suspend fun clear() {
        cache.clear()
        prefs
            .edit()
            .clear()
            .commit()
    }

    /**
     * Creates the SharedPreferences instance.
     *
     * @return The SharedPreferences instance.
     */
    protected abstract fun createSharedPreferences(): SharedPreferences

}