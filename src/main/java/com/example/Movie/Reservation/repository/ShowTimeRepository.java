package com.example.Movie.Reservation.repository;

import com.example.Movie.Reservation.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface ShowTimeRepository extends JpaRepository<ShowTime,Long> {
   List<ShowTime> findByMovieIdAndStartTimeBetween(Long movieId, LocalDateTime startTime, LocalDateTime endTime);
    @Query("SELECT s FROM ShowTime s WHERE s.movie.id = :movieId AND s.startTime BETWEEN :startOfDay AND :endOfDay AND s.startTime > :currentTime")
    List<ShowTime> findUpcomingShowtimesByMovieAndDate(
            @Param("movieId") Long movieId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            @Param("currentTime") LocalDateTime currentTime
    );
}
