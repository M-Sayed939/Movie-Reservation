package com.example.Movie.Reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reserved_seats", uniqueConstraints = @UniqueConstraint(columnNames = {"showtime_id", "seat_number"}))
@Data
@NoArgsConstructor
public class ReservedSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="reservation_id", nullable = false)
    private Reservation reservation;
    @ManyToOne
    @JoinColumn(name="showtime_id", nullable = false)
    private ShowTime showTime;
    @Column(nullable = false)
    private String seatNumber;
}
