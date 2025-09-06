package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.OmdbMovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OmdbService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${omdb.api.key}")
    private String apiKey;
//    private final String OMDB_API_URL = "http://www.omdbapi.com/?i=tt3896198&apikey=e254a50c";
private final String OMDB_URL = "https://www.omdbapi.com/?apikey={apiKey}&i={imdbId}";
    public OmdbMovieDto fetchMovieDetails(String imdbId) {
        String url = OMDB_URL.replace("{apiKey}", apiKey).replace("{imdbId}", imdbId);
        return restTemplate.getForObject(url, OmdbMovieDto.class);
    }
}
