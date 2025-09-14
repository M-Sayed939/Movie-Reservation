package com.example.Movie.Reservation.controller;

import com.example.Movie.Reservation.dto.OmdbMovieDto;
import com.example.Movie.Reservation.model.Movie;
import com.example.Movie.Reservation.repository.GenreRepository;
import com.example.Movie.Reservation.service.MovieService;
import com.example.Movie.Reservation.service.OmdbService;
import com.example.Movie.Reservation.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class MovieAdminController {
    @Autowired
    private MovieService movieService;
//    @Autowired
//    private TmdbService tmdbService;
//    private OmdbService omdbService;
//
//    @GetMapping("/omdb/{imdbId}")
//    public ResponseEntity<OmdbMovieDto> searchOmdbByImdbI(@PathVariable String imdbId) {
//        OmdbMovieDto movie = omdbService.fetchMovieDetails(imdbId);
//        return ResponseEntity.ok(movie);
//    }
//    @PostMapping("/movies/import/{imdbId}")
//    public ResponseEntity<Movie> importMovie(@PathVariable String imdbId) {
//        Movie importedMovie = movieService.importMovie(imdbId);
//        return ResponseEntity.ok(importedMovie);
//    }
    @PostMapping("/movies/import-popular")
    public ResponseEntity<List<Movie>> importMoviesPopular() {
        List<Movie> importedMovies = movieService.importPopularMovies();
        return ResponseEntity.ok(importedMovies);
    }
}
