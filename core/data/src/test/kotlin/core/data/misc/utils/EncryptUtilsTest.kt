package core.data.misc.utils

import core.testing.BaseUnitTest
import org.junit.Assert
import kotlin.test.Test

class EncryptUtilsTest : BaseUnitTest() {

    @Test
    fun `aes encryption`() {
        val text = "AAABBBCCC"
        val password = EncryptUtils.generatePassword()
        val encoded = EncryptUtils.encryptAes(password, text)
        val decoded = EncryptUtils.decryptAes(password, encoded)
        Assert.assertNotEquals(text, encoded)
        Assert.assertEquals(text, decoded)
    }

}