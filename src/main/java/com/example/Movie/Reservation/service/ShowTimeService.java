package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.ShowTimeDto;
import com.example.Movie.Reservation.dto.ShowTimeResponseDto;
import com.example.Movie.Reservation.model.CinemaHall;
import com.example.Movie.Reservation.model.Movie;
import com.example.Movie.Reservation.model.ShowTime;
import com.example.Movie.Reservation.repository.CinemaHallRepository;
import com.example.Movie.Reservation.repository.MovieRepository;
import com.example.Movie.Reservation.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShowTimeService {
    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CinemaHallRepository cinemaHallRepository;
    public ShowTime createShowTime(ShowTimeDto showTimeDto) {
        Movie movie = movieRepository.findById(showTimeDto.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));

        CinemaHall cinemaHall = cinemaHallRepository.findById(showTimeDto.getCinemaHallId()).orElseThrow(() -> new RuntimeException("Cinema Hall not found"));
        ShowTime showTime = new ShowTime();
        showTime.setMovie(movie);
        showTime.setCinemaHall(cinemaHall);
        showTime.setStartTime(showTimeDto.getStartTime());
        showTime.setPrice(showTimeDto.getPrice());
        showTime.setEndTime(showTimeDto.getStartTime().plusMinutes(movie.getDuration()));
        return showTimeRepository.save(showTime);

    }
    public List<ShowTimeResponseDto> findShowTimesByMovieAndDate(Long movieId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        LocalDateTime now = LocalDateTime.now();

        List<ShowTime> showTimes = showTimeRepository.findUpcomingShowtimesByMovieAndDate(movieId, startOfDay, endOfDay, now);

        return showTimes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ShowTimeResponseDto convertToDto(ShowTime showTime) {
        ShowTimeResponseDto dto = new ShowTimeResponseDto();
        dto.setId(showTime.getId());
        dto.setStartTime(showTime.getStartTime());
        dto.setEndTime(showTime.getEndTime());
        dto.setPrice(showTime.getPrice());
        dto.setMovieTitle(showTime.getMovie().getTitle());
        dto.setCinemaHallName(showTime.getCinemaHall().getName());
        return dto;
    }

}
