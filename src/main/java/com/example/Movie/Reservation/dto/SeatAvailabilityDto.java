package com.example.Movie.Reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SeatAvailabilityDto {
    private int totalRows;
    private int totalColumns;
    private List<String> reservedSeatNumbers;
}
