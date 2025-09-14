package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.CreateReservationRequest;
import com.example.Movie.Reservation.dto.ReservationResponseDto;
import com.example.Movie.Reservation.dto.ReservationStatus;
import com.example.Movie.Reservation.dto.SeatAvailabilityDto;
import com.example.Movie.Reservation.model.*;
import com.example.Movie.Reservation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservedSeatRepository reservedSeatRepository;
    @Autowired
    private ShowTimeRepository showTimeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CinemaHallRepository cinemaHallRepository;

    public SeatAvailabilityDto getSeatAvailability(Long showtimeId) {
        ShowTime showtime = showTimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        CinemaHall cinemaHall = showtime.getCinemaHall();
        List<String> reservedSeats = reservedSeatRepository.findReservedSeatNumbersByShowtimeId(showtimeId);

//        int totalRows = cinemaHall.getSeatRows();
//        int totalColumns = cinemaHall.getSeatColumns();

        return new SeatAvailabilityDto(cinemaHall.getSeatRows(), cinemaHall.getSeatColumns(), reservedSeats);
    }

    @Transactional
    public ReservationResponseDto createReservation(CreateReservationRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ShowTime showtime = showTimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        List<String> alreadyReserved = reservedSeatRepository.findReservedSeatNumbersByShowtimeId(request.getShowtimeId());
        boolean isAnySeatTaken = request.getSeatNumbers().stream()
                .anyMatch(alreadyReserved::contains);
        if (isAnySeatTaken) {
            throw new RuntimeException("One or more selected seats are already reserved.");
        }
//        for (String requestedSeat : request.getSeatNumbers()) {
//            if (alreadyReserved.contains(requestedSeat)) {
//                throw new RuntimeException("Seat " + requestedSeat + " is already reserved.");
//            }
//        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowTime(showtime);
        reservation.setStatus(ReservationStatus.BOOKED);
        reservation.setDate(LocalDateTime.now());
        Reservation savedReservation = reservationRepository.save(reservation);
        List<ReservedSeat> seatsToReserve = request.getSeatNumbers().stream().map(seatNumber -> {
            ReservedSeat reservedSeat = new ReservedSeat();
            reservedSeat.setReservation(savedReservation);
            reservedSeat.setShowTime(showtime);
            reservedSeat.setSeatNumber(seatNumber);
            return reservedSeat;
        }).collect(Collectors.toList());

        reservedSeatRepository.saveAll(seatsToReserve);
        savedReservation.setReservedSeats(seatsToReserve);
        return new ReservationResponseDto(savedReservation);
    }
    public List<ReservationResponseDto> getMyReservations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Reservation>  reservations =reservationRepository.findByUserId(user.getId());
        return reservations.stream().map(ReservationResponseDto::new).collect(Collectors.toList());
    }

}
