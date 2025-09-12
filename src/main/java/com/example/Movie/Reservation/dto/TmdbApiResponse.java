package com.example.Movie.Reservation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbApiResponse {
    private List<TmdbMovieDto> results;
}
