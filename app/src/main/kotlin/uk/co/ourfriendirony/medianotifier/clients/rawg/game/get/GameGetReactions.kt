package uk.co.ourfriendirony.medianotifier.clients.rawg.game.get

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("2", "11", "12")
class GameGetReactions {
    @JsonProperty("2")
    private var _a2: Int? = null

    @JsonProperty("11")
    private var _a11: Int? = null

    @JsonProperty("12")
    private var _a12: Int? = null

    @JsonProperty("2")
    fun get2(): Int? {
        return _a2
    }

    @JsonProperty("2")
    fun set2(_2: Int?) {
        this._a2 = _2
    }

    @JsonProperty("11")
    fun get11(): Int? {
        return _a11
    }

    @JsonProperty("11")
    fun set11(_11: Int?) {
        this._a11 = _11
    }

    @JsonProperty("12")
    fun get12(): Int? {
        return _a12
    }

    @JsonProperty("12")
    fun set12(_12: Int?) {
        this._a12 = _12
    }
}