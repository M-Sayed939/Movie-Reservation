package com.example.Movie.Reservation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateReservationRequest {
    @NotNull
    private Long showtimeId;
    @NotEmpty
    private List<String> seatNumbers;
}
