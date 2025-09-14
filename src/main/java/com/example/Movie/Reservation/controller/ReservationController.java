package com.example.Movie.Reservation.controller;

import com.example.Movie.Reservation.dto.CreateReservationRequest;
import com.example.Movie.Reservation.dto.ReservationResponseDto;
import com.example.Movie.Reservation.dto.SeatAvailabilityDto;
import com.example.Movie.Reservation.model.Reservation;
import com.example.Movie.Reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

//    @GetMapping("/showtimes/{showtimeId}/seats")
//    public ResponseEntity<SeatAvailabilityDto> getSeatAvailability(@PathVariable Long showtimeId) {
//        return ResponseEntity.ok(reservationService.getSeatAvailability(showtimeId));
//    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @Valid @RequestBody CreateReservationRequest reservationRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        ReservationResponseDto response = reservationService.createReservation(reservationRequest, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    // Endpoint for a logged-in user to view their own reservations
    @GetMapping("/reservations/my-reservations")
    public ResponseEntity<List<ReservationResponseDto>> getMyReservations(@AuthenticationPrincipal UserDetails userDetails) {
        List<ReservationResponseDto> reservations = reservationService.getMyReservations(userDetails.getUsername());
//        List<ReservationResponseDto> responseDtos = reservations.stream()
//                .map(ReservationResponseDto::new)
//                .collect(Collectors.toList());
        return ResponseEntity.ok(reservations);
    }
}
