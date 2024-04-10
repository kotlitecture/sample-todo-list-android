package core.data.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
 * A [SerializationStrategy] implementation that uses JSON serialization for conversion.
 *
 * @param serializer The serializer for the object type [T].
 */
data class JsonStrategy<T>(private val serializer: KSerializer<T>, private val type: Class<T>) : SerializationStrategy<T> {

    override fun toObject(from: String): T {
        return Json.decodeFromString(serializer, from)
    }

    override fun toString(from: T): String {
        return Json.encodeToString(serializer, from)
    }

    override fun getType(): Class<T> {
        return type
    }

    companion object {
        inline fun <reified D> create(serializer: KSerializer<D>): SerializationStrategy<D> = JsonStrategy(serializer, D::class.java)
    }

}