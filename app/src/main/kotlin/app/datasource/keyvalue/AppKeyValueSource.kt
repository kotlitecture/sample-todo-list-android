package app.datasource.keyvalue

import android.app.Application
import core.data.datasource.keyvalue.SharedPreferencesSource
import core.data.serialization.NoSerializationStrategy

/**
 * Decorator class for working with key-value storage on the app level.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppKeyValueSource(app: Application) : SharedPreferencesSource(app) {

    /**
     * Reads the value associated with the specified key from encrypted storage.
     *
     * @param key The key to read.
     * @return The value associated with the key, or `null` if the key does not exist or cannot be decrypted.
     * @throws IllegalStateException if serialization/deserialization fails or if the requested type is not supported.
     */
    suspend inline fun <reified T> read(key: String): T? {
        return read(key, NoSerializationStrategy.create())
    }

    /**
     * Saves the specified key-value pair into encrypted storage.
     *
     * @param key The key to save.
     * @param value The value to save.
     * @throws IllegalStateException if serialization/deserialization fails or if the requested type is not supported.
     */
    suspend inline fun <reified T> save(key: String, value: T) {
        save(key, value, NoSerializationStrategy.create())
    }

}