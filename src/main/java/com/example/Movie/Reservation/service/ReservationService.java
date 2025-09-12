package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.CreateReservationRequest;
import com.example.Movie.Reservation.dto.ReservationStatus;
import com.example.Movie.Reservation.dto.SeatAvailabilityDto;
import com.example.Movie.Reservation.model.*;
import com.example.Movie.Reservation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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

        int totalRows = 10;
        int totalColumns = 15;

        return new SeatAvailabilityDto(totalRows, totalColumns, reservedSeats);
    }

    @Transactional
    public Reservation createReservation(CreateReservationRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShowTime showtime = showTimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        // Check if seats are already booked for this showtime
        List<String> alreadyReserved = reservedSeatRepository.findReservedSeatNumbersByShowtimeId(showtime.getId());
        for (String requestedSeat : request.getSeatNumbers()) {
            if (alreadyReserved.contains(requestedSeat)) {
                throw new RuntimeException("Seat " + requestedSeat + " is already reserved.");
            }
        }

        // Create the main reservation record
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

        return savedReservation;
    }

    public List<Reservation> getMyReservations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findByUserId(user.getId());
    }

}
