package com.example.workouttracker.controller;

import com.example.workouttracker.DTO.JwtResponseDTO;
import com.example.workouttracker.DTO.UserLoginDTO;
import com.example.workouttracker.DTO.UserRegistrationDTO;
import com.example.workouttracker.security.JwtTokenProvider;
import com.example.workouttracker.service.UserService;
import com.example.workouttracker.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    // Constructor with all dependencies
    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDto) {
        if (userService.findByUsername(registrationDto.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        User user = new User(registrationDto.getUsername(), registrationDto.getPassword());
        userService.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponseDTO(jwt));
    }
}