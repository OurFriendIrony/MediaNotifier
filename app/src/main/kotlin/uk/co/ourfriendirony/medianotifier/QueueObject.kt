package uk.co.ourfriendirony.medianotifier

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(
    "id",
    "type"
)
class QueueObject {
    @JsonProperty("id")
    var id: String? = null

    @JsonProperty("type")
    var type: String? = null
}