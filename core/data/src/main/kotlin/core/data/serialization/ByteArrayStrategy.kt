package core.data.serialization

import android.util.Base64

/**
 * A [SerializationStrategy] implementation that serialize only ByteArray.
 */
object ByteArrayStrategy : SerializationStrategy<ByteArray> {

    override fun toObject(from: String): ByteArray {
        return Base64.decode(from, Base64.DEFAULT)
    }

    override fun toString(from: ByteArray): String {
        return Base64.encodeToString(from, Base64.DEFAULT)
    }

    override fun getType(): Class<ByteArray> {
        return ByteArray::class.java
    }

}