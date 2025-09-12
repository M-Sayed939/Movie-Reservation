package com.example.Movie.Reservation.dto;

import com.example.Movie.Reservation.model.Reservation;
import com.example.Movie.Reservation.model.ReservedSeat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private String movieTitle;
    private String theaterName;
    private LocalDateTime showtime;
    private List<String> reservedSeats;
    private String status;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.movieTitle = reservation.getShowTime().getMovie().getTitle();
        this.theaterName = reservation.getShowTime().getCinemaHall().getName();
        this.showtime = reservation.getShowTime().getStartTime();
        this.status = reservation.getStatus().toString();
        this.reservedSeats = reservation.getReservedSeats().stream()
                .map(ReservedSeat::getSeatNumber)
                .collect(Collectors.toList());
    }
}
