package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.ReservedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservedSeatRepository extends JpaRepository<ReservedSeat, Integer> {

    @Query("SELECT rs.seatNumber FROM ReservedSeat rs WHERE rs.showTime.id = :showtimeId")
    List<String> findReservedSeatNumbersByShowtimeId(@Param("showtimeId") Long showtimeId);

}
