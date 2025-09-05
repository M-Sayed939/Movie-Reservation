package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Optional<Movie> findByImdbId(String imdbId);
}
