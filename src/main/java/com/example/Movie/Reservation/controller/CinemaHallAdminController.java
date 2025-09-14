package com.example.Movie.Reservation.controller;

import com.example.Movie.Reservation.dto.CinemaHallDto;
import com.example.Movie.Reservation.model.CinemaHall;
import com.example.Movie.Reservation.service.CinemaHallService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/cinemahalls")
@PreAuthorize("hasRole('ADMIN')")
public class CinemaHallAdminController {
    @Autowired
    private CinemaHallService cinemaHallService;

    @PostMapping
    public ResponseEntity<CinemaHall> createCinemaHall(@Valid @RequestBody CinemaHallDto cinemaHallDto) {
        CinemaHall createdCinemaHall = cinemaHallService.createCinemaHall(cinemaHallDto);
        return ResponseEntity.ok(createdCinemaHall);
    }
}
