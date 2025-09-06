package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.OmdbMovieDto;
import com.example.Movie.Reservation.model.Genre;
import com.example.Movie.Reservation.model.Movie;
import com.example.Movie.Reservation.repository.GenreRepository;
import com.example.Movie.Reservation.repository.MovieRepository;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private OmdbService omdbService;

    @Transactional
    public Movie importMovie(String imdbId) {
        if(movieRepository.findByImdbId(imdbId).isPresent()) {
            throw new RuntimeException("Movie already exists");

        }
        OmdbMovieDto omdbMovie = omdbService.fetchMovieDetails(imdbId);
        if (omdbMovie == null || omdbMovie.getTitle() == null) {
            throw new RuntimeException("Movie not found in OMDB");
        }
        Movie movie = new Movie();
        movie.setImdbId(omdbMovie.getImdbId());
        movie.setTitle(omdbMovie.getTitle());
        movie.setDescription(omdbMovie.getDescription());
        movie.setPosterUrl(omdbMovie.getPosterUrl());
        try{
            int duration = Integer.parseInt(omdbMovie.getRuntime().replaceAll("[^\\d]", ""));
            movie.setDuration(duration);
        }catch (NumberFormatException e) {
            movie.setDuration(0);
        }
        Set<Genre> genres = new HashSet<>();
        if (omdbMovie.getGenre() != null) {
            String[] genreNames = omdbMovie.getGenre().split(",\\s*");
            for (String name : genreNames) {
                Genre genre = genreRepository.findByName(name)
                        .orElseGet(() -> {
                            Genre newGenre = new Genre();
                            newGenre.setName(name);
                            return genreRepository.save(newGenre);
                        });
                genres.add(genre);
            }
        }
        movie.setGenres(genres);
        return movieRepository.save(movie);

    }
}
