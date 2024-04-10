package core.data.misc.utils

import com.google.gson.annotations.SerializedName
import core.testing.BaseUnitTest
import org.junit.Assert
import java.util.Date
import kotlin.test.Test

class GsonUtilsTest : BaseUnitTest() {

    @Test
    fun `parse object with date`() {
        val dateString = "2023-04-12T16:54:26"
        val text = "{ date: '$dateString' }"
        val dateHolder = GsonUtils.toObject(text, DateHolder::class.java)!!
        Assert.assertNotNull(dateHolder.date)
    }

    data class DateHolder(
        @SerializedName("date")
        val date: Date?
    )

}