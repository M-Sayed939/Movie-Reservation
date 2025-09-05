package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}
