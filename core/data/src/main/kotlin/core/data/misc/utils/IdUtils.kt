package core.data.misc.utils

import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object IdUtils {

    private val random = SecureRandom()

    fun autoId(): String {
        val builder = StringBuilder()
        val maxRandom = AUTO_ID_ALPHABET.length
        for (i in 0..19) {
            builder.append(AUTO_ID_ALPHABET[random.nextInt(maxRandom)])
        }
        return builder.toString()
    }

    fun timeBasedId(prefix: String): String {
        var prefixRef = prefix
        prefixRef = prefixRef.trim { it <= ' ' }
        prefixRef = prefixRef.replace('.', '_')
        prefixRef = prefixRef.replace(':', '_')
        val suffix = SimpleDateFormat("yyMMdd_hhmmss", Locale.ROOT).format(Date())
        return buildString(prefixRef, "_", suffix)
    }

    private fun buildString(vararg strings: String): String {
        var size = 0
        for (string in strings) {
            size += string.length
        }
        val builder = StringBuilder(size)
        for (string in strings) {
            builder.append(string)
        }
        return builder.toString()
    }

    private const val AUTO_ID_ALPHABET =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
}