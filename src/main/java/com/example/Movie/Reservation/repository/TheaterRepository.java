package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
