
        package uk.co.ourfriendirony.medianotifier.clients.musicbrainz.release.get;

                import java.util.HashMap;
                import java.util.Map;
                import com.fasterxml.jackson.annotation.JsonAnyGetter;
                import com.fasterxml.jackson.annotation.JsonAnySetter;
                import com.fasterxml.jackson.annotation.JsonIgnore;
                import com.fasterxml.jackson.annotation.JsonInclude;
                import com.fasterxml.jackson.annotation.JsonProperty;
                import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "language",
        "script"
})
public class ReleaseGetTextRepresentation {

    @JsonProperty("language")
    private String language;
    @JsonProperty("script")
    private String script;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("script")
    public String getScript() {
        return script;
    }

    @JsonProperty("script")
    public void setScript(String script) {
        this.script = script;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}