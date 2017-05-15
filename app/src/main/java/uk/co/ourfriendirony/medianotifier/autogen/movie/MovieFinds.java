package uk.co.ourfriendirony.medianotifier.autogen.movie;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "results"
})
public class MovieFinds {
    @JsonProperty("results")
    private List<Movie> results = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("results")
    public List<Movie> getMovies() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getMoviesWithDates() {
        List<Movie> movies = new ArrayList<>();
        for (Movie movie : getMovies()) {
            if (!("".equals(movie.getReleaseDate())) && movie.getReleaseDate() != null) {
                movies.add(movie);
            }
        }
        return movies;
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