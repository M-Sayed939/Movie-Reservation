package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
