package com.example.Movie.Reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CinemaHallDto {
    @NotBlank(message = "Cinema hall name is required")
    private String name;
    @Positive(message = "Number of seat rows must be positive")
    private int seatRows;
    @Positive(message = "Number of seat columns must be positive")
    private int seatColumns;

}
