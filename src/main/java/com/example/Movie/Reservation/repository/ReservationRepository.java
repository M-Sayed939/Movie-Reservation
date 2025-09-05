package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.showTime.startTime > :currentTime AND r.status = 'CONFIRMED' ORDER BY r.showTime.startTime")
    List<Reservation> findUpcomingReservationsForUser(@Param("userId") UUID userId, @Param("currentTime") LocalDateTime currentTime);
}
