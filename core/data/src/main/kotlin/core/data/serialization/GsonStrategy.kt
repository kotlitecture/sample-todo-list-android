package core.data.serialization

import core.data.misc.utils.GsonUtils

/**
 * A [SerializationStrategy] implementation that uses GSON serialization for conversion.
 *
 * @param type The type of object.
 */
data class GsonStrategy<T>(private val type: Class<T>) : SerializationStrategy<T> {

    override fun toObject(from: String): T? {
        return GsonUtils.toObject(from, type)
    }

    override fun toString(from: T): String? {
        return GsonUtils.toString(from)
    }

    override fun getType(): Class<T> {
        return type
    }

    companion object {
        inline fun <reified D> create(): SerializationStrategy<D> = GsonStrategy(D::class.java)
    }

}