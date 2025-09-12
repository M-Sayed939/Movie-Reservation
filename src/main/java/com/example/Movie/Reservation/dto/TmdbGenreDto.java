package com.example.Movie.Reservation.dto;

import com.example.Movie.Reservation.model.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbGenreDto {
    private List<Genre> genres;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Genre {
        private int id;
        private String name;
    }
}
