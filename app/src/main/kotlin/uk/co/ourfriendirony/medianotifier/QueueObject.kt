package uk.co.ourfriendirony.medianotifier

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import uk.co.ourfriendirony.medianotifier.activities.chars
import uk.co.ourfriendirony.medianotifier.activities.objectMapper

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "id",
    "type"
)
class QueueObject(val id: String, val type: String) {
    fun toBytes(): ByteArray {
        return objectMapper.writeValueAsBytes(this)
    }
}
//
//fun toBytes(queueObject: QueueObject): ByteArray {
//    val value = QueueObject("1", chars.random().toString())
//    return objectMapper.writeValueAsBytes(value)
//}

fun toObject(byteArray: ByteArray): QueueObject {
    return objectMapper.readValue(byteArray, QueueObject::class.java)
}