package com.example.Movie.Reservation.controller;

import com.example.Movie.Reservation.dto.MovieResponseDto;
import com.example.Movie.Reservation.dto.ShowTimeResponseDto;
import com.example.Movie.Reservation.service.MovieService;
import com.example.Movie.Reservation.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable("id") Long id) {
        return movieService.getMovieById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{movieid}/showtimes")
    public ResponseEntity<List<ShowTimeResponseDto>> getShowtimesForMovieAndDate(
            @PathVariable Long movieId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ShowTimeResponseDto> showtimes = showTimeService.findShowTimesByMovieAndDate(movieId, date);
        return ResponseEntity.ok(showtimes);
    }

}
