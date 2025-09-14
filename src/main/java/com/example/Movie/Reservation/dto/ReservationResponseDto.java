package com.example.Movie.Reservation.dto;

import com.example.Movie.Reservation.model.Reservation;
import com.example.Movie.Reservation.model.ReservedSeat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private String movieTitle;
    private String cinemaHallName;
    private LocalDateTime startTime;
    private List<String> reservedSeats;
    private ReservationStatus status;
    private UUID userId;


    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.movieTitle = reservation.getShowTime().getMovie().getTitle();
        this.cinemaHallName = reservation.getShowTime().getCinemaHall().getName();
        this.startTime = reservation.getShowTime().getStartTime();
        this.status = reservation.getStatus();
        this.userId = reservation.getUser().getId();
        this.reservedSeats = reservation.getReservedSeats().stream()
                .map(ReservedSeat::getSeatNumber)
                .collect(Collectors.toList());
    }
}
