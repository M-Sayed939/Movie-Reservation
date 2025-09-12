package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.TmdbApiResponse;
import com.example.Movie.Reservation.dto.TmdbGenreDto;
import com.example.Movie.Reservation.dto.TmdbMovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TmdbService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3";

    public TmdbApiResponse getPopularMovies() {
        String url = TMDB_BASE_URL + "/movie/now_playing?api_key=" + apiKey;
        return restTemplate.getForObject(url, TmdbApiResponse.class);
    }

    public TmdbMovieDto getMovieDetails(Long tmdbId) {
        String url = TMDB_BASE_URL + "/movie/" + tmdbId + "?api_key=" + apiKey;
        return restTemplate.getForObject(url, TmdbMovieDto.class);
    }

    public Map<Integer, String> getGenreMap() {
        String url = TMDB_BASE_URL + "/genre/movie/list?api_key=" + apiKey;
        TmdbGenreDto response = restTemplate.getForObject(url, TmdbGenreDto.class);
        if (response != null && response.getGenres() != null) {
            return response.getGenres().stream()
                    .collect(Collectors.toMap(TmdbGenreDto.Genre::getId, TmdbGenreDto.Genre::getName));
        }
        return Map.of();
    }
}
