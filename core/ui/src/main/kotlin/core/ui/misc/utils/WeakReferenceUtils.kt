@file:Suppress("UNCHECKED_CAST")

package core.ui.misc.utils

import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

/**
 * Utility class for managing weak references.
 */
object WeakReferenceUtils {

    /** Cache to store weak references. */
    private val cache = ConcurrentHashMap<String, WeakReference<*>>()

    /**
     * Replaces the value associated with the given key in the cache with a new weak reference
     * to the provided value.
     *
     * @param key The key associated with the value in the cache.
     * @param value The value to be stored in the cache as a weak reference.
     * @return The previous value associated with the key, or null if there was no mapping for the key.
     */
    fun <T> replace(key: String, value: T): T? = cache.replace(key, WeakReference(value))?.get() as? T

}