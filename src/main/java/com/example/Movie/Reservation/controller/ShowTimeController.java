package com.example.Movie.Reservation.controller;

import com.example.Movie.Reservation.dto.SeatAvailabilityDto;
import com.example.Movie.Reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/showtimes")
public class ShowTimeController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/{showtimeId}/seats")
    public ResponseEntity<SeatAvailabilityDto> getSeatAvailability(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(reservationService.getSeatAvailability(showtimeId));
    }
}
