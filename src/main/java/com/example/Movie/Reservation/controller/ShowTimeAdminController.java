package com.example.Movie.Reservation.controller;

import com.example.Movie.Reservation.dto.ShowTimeDto;
import com.example.Movie.Reservation.model.ShowTime;
import com.example.Movie.Reservation.service.ShowTimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/showtimes")
@PreAuthorize("hasRole('ADMIN')")
public class ShowTimeAdminController {
    @Autowired
    private ShowTimeService showTimeService;
    @PostMapping
    public ResponseEntity<ShowTime> createShowTime(@Valid @RequestBody ShowTimeDto showTimeDto) {
        ShowTime createdShowTime = showTimeService.createShowTime(showTimeDto);
        return ResponseEntity.ok(createdShowTime);
    }
}
