package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ShowTimeRepository extends JpaRepository<ShowTime,Long> {
    @Query("SELECT s FROM ShowTime s WHERE s.movie.id = :movieId AND FUNCTION('DATE', s.startTime) = :date ORDER BY s.startTime")
    List<ShowTime> findByMovieId(@Param("movieId") UUID movieId, @Param("date") LocalDate date);
    @Query("SELECT s FROM ShowTime s WHERE s.theater.id = :theaterId AND s.startTime < :endTime AND :startTime < FUNCTION('ADDTIME', s.startTime, s.movie.duration|| ' MINUTES')")
    List<ShowTime> findOverlappingShowTimes(@Param("theaterId") Integer theaterId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
