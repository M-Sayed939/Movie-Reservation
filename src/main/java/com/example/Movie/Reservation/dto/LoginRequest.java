package com.example.Movie.Reservation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Write The Username")
    private String username;
    @NotBlank(message = "Write The Password")
    private String password;
}
