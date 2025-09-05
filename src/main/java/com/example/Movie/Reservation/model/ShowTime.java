package com.example.Movie.Reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "showtimes")
@Data
@NoArgsConstructor
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="movie_id", nullable = false)
    private Movie movie;
    @ManyToOne
    @JoinColumn(name="theater_id", nullable = false)
    private Theater theater;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private BigDecimal price;

}
