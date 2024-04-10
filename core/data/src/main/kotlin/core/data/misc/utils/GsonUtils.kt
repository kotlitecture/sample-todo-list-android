package core.data.misc.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object GsonUtils {

    private val gson: Gson by lazy { configure(GsonBuilder()).create() }

    fun configure(builder: GsonBuilder): GsonBuilder = builder
        .setLenient()
        .registerTypeAdapter(Date::class.java, GsonUtcDateAdapter())

    fun toString(obj: Any?): String? =
        runCatching { obj?.let(gson::toJson) }.getOrNull()

    fun <T> toObject(json: String?, clazz: Class<T>): T? =
        runCatching { json?.let { gson.fromJson(json, clazz) } }
            .getOrNull()

    fun <T> toObject(json: String?, type: TypeToken<T>): T? =
        runCatching { json?.let { gson.fromJson(json, type) } }.getOrNull()

    private class GsonUtcDateAdapter : JsonSerializer<Date>, JsonDeserializer<Date> {

        private val dateTimeFullFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private val dateTimeMediumFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        private val dateTimeShortFormat = "yyyy-MM-dd'T'HH:mm:ss"
        private val dateFormatDashes = "yyyy-MM-dd"
        private val timeFormatDots = "HH:mm:ss"
        private val timeFormatShortDots = "HH:mm"

        private val dateTimeFullFormatter = SimpleDateFormat(dateTimeFullFormat, Locale.ROOT)
            .apply { timeZone = TimeZone.getTimeZone("GMT") }

        private val dateTimeMediumFormatter = SimpleDateFormat(dateTimeMediumFormat, Locale.ROOT)
            .apply { timeZone = TimeZone.getTimeZone("GMT") }

        private val dateTimeShortFormatter = SimpleDateFormat(dateTimeShortFormat, Locale.ROOT)
            .apply { timeZone = TimeZone.getTimeZone("GMT") }

        private val dateDashesFormatter = SimpleDateFormat(dateFormatDashes, Locale.ROOT)
            .apply { timeZone = TimeZone.getTimeZone("GMT") }

        private val timeDotsFormatter = SimpleDateFormat(timeFormatDots, Locale.ROOT)
            .apply { timeZone = TimeZone.getTimeZone("GMT") }

        private val timeShortDotsFormatter = SimpleDateFormat(timeFormatShortDots, Locale.ROOT)
            .apply { timeZone = TimeZone.getTimeZone("GMT") }

        override fun serialize(
            date: Date,
            type: Type,
            jsonSerializationContext: JsonSerializationContext
        ): JsonElement {
            return JsonPrimitive(dateTimeFullFormatter.format(date))
        }

        override fun deserialize(
            jsonElement: JsonElement,
            type: Type,
            jsonDeserializationContext: JsonDeserializationContext
        ): Date {
            try {
                val text = jsonElement.asString
                val formatter = when {
                    text.length == timeFormatDots.length && text.contains(":") -> timeDotsFormatter
                    text.length == timeFormatShortDots.length && text.contains(":") -> timeShortDotsFormatter
                    text.length == dateFormatDashes.length && text.contains("-") -> dateDashesFormatter
                    text.length == dateTimeMediumFormat.length - 4 -> dateTimeMediumFormatter
                    text.length == dateTimeShortFormat.length - 2 -> dateTimeShortFormatter
                    else -> dateTimeFullFormatter
                }
                return formatter.parse(text)!!
            } catch (e: ParseException) {
                throw JsonParseException(e)
            }
        }
    }

}