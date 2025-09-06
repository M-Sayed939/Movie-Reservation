package com.example.Movie.Reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OmdbMovieDto {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Plot")
    private String description;
    @JsonProperty("Poster")
    private String posterUrl;
    @JsonProperty("imdbID")
    private String imdbId;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("Runtime")
    private String runtime;
}
