package org.example.healthcare.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.auth.LoginRequestDTO;
import org.example.healthcare.dto.auth.UserResquestDTO;
import org.example.healthcare.dto.auth.UserResponseDTO;
import org.example.healthcare.model.User;
import org.example.healthcare.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserResquestDTO request) {
        User user = authService.regisrer(request);
        return ResponseEntity.ok(toUserResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof User user) {
            return ResponseEntity.ok(toUserResponse(user));
        }
        if (principal instanceof UserDetails userDetails) {
            return ResponseEntity.ok(new UserResponseDTO(null, null, userDetails.getUsername(), null));
        }
        return ResponseEntity.badRequest().build();
    }

    private UserResponseDTO toUserResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}