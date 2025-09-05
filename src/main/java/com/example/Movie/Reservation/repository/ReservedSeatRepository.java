package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.ReservedSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservedSeatRepository extends JpaRepository<ReservedSeat, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT rs FROM ReservedSeat rs WHERE rs.showTime.id = :showTimeId AND rs.seatNumber IN :seatNumbers")
    List<ReservedSeat> findByShowtimeIdAndSeatNumberInWithLock(@Param("showtimeId") UUID showTimeId, @Param("seatNumbers") List<String> seatNumbers);

    List<ReservedSeat> findByShowtimeId(UUID showTimeId);

}
