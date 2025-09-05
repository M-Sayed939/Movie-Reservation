package com.example.Movie.Reservation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReservationRequest {
    @NotNull
    private Long showtimeId;
    @NotEmpty
    private String seatNumbers;
}
