package com.example.Movie.Reservation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 20)
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    private String password;

    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 50)
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
}
