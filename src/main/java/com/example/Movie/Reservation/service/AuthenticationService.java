package com.example.Movie.Reservation.service;

import com.example.Movie.Reservation.dto.LoginRequest;
import com.example.Movie.Reservation.dto.SignUpRequest;
import com.example.Movie.Reservation.model.Role;
import com.example.Movie.Reservation.model.User;
import com.example.Movie.Reservation.repository.UserRepository;
import com.example.Movie.Reservation.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User signUp(SignUpRequest signUpRequest) {
        if(userRepository.findByUsername(signUpRequest.getUsername()).isPresent()){
            throw new RuntimeException("Username is already in use");
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setRole(Role.ROLE_USER);
        return  userRepository.save(user);
    }
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        return jwtTokenProvider.generateToken(authentication);
    }
}
