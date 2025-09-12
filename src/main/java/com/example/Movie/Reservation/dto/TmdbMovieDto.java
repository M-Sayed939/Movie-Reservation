package com.example.Movie.Reservation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovieDto {
    private Long id;
    private String title;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("overview")
    private String description;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    @JsonProperty("runtime")
    private Integer duration;
}
