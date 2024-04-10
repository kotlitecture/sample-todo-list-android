@file:OptIn(ExperimentalStdlibApi::class)

package core.data.misc.utils

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptUtils {

    private const val AES_PKCS = "AES/CBC/PKCS5Padding"
    private const val AES = "AES"

    fun generatePassword(): ByteArray {
        val keygen = KeyGenerator.getInstance(AES)
        keygen.init(256)
        return keygen.generateKey().encoded
    }

    fun encryptAes(password: ByteArray, text: String): String {
        val keySpec = SecretKeySpec(password, AES)
        val ivSpec = IvParameterSpec(ByteArray(16))
        val cipher = Cipher.getInstance(AES_PKCS)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        return toHex(encryptedBytes)
    }

    fun decryptAes(password: ByteArray, text: String): String {
        val keySpec = SecretKeySpec(password, AES)
        val ivSpec = IvParameterSpec(ByteArray(16))
        val cipher = Cipher.getInstance(AES_PKCS)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decryptedBytes = cipher.doFinal(fromHex(text))
        return String(decryptedBytes, Charsets.UTF_8)
    }

    private fun toHex(from: ByteArray): String {
        return from.toHexString()
    }

    private fun fromHex(from: String): ByteArray {
        val len = from.length / 2
        val result = ByteArray(len)
        for (i in 0 until len) {
            result[i] = Integer.valueOf(from.substring(2 * i, 2 * i + 2), 16).toByte()
        }
        return result
    }

}