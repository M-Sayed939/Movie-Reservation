package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.CinemaHallDto;
import com.example.Movie.Reservation.model.CinemaHall;
import com.example.Movie.Reservation.repository.CinemaHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaHallService {
    @Autowired
    private CinemaHallRepository cinemaHallRepository;
    public CinemaHall createCinemaHall(CinemaHallDto cinemaHallDto) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setName(cinemaHallDto.getName());
        cinemaHall.setSeatRows(cinemaHallDto.getSeatRows());
        cinemaHall.setSeatColumns(cinemaHallDto.getSeatColumns());
        return cinemaHallRepository.save(cinemaHall);

    }
}
