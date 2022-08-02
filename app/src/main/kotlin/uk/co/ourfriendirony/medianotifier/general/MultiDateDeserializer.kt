package uk.co.ourfriendirony.medianotifier.general

import android.util.Log
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MultiDateDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Date?>(vc) {
    @Throws(IOException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): Date? {
        val node = jp.codec.readTree<JsonNode>(jp)
        val date = node.textValue()
        for (DATE_FORMAT in DATE_FORMATS) {
            try {
                return SimpleDateFormat(DATE_FORMAT, Locale.UK).parse(date)
            } catch (e: ParseException) {
                // Ignore and check the next deserialise pattern
            }
        }
        val errorMessage = "Unparseable date: \"" + date + "\". Supported formats: " + Arrays.toString(DATE_FORMATS)
        Log.w("[DATE PARSE]", errorMessage)
        return null
    }

    companion object {
        private const val serialVersionUID = 1L
        private val DATE_FORMATS = arrayOf(
                "yyyy-MM-dd",
                "yyyy-MM",
                "yyyy"
        )
    }
}