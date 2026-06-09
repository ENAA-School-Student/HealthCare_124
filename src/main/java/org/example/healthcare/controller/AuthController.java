package org.example.healthcare.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.auth.LoginRequestDTO;
import org.example.healthcare.dto.auth.UserResquestDTO;
import org.example.healthcare.dto.auth.UserResponseDTO;
import org.example.healthcare.model.User;
import org.example.healthcare.repository.UserRedpository;
import org.example.healthcare.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRedpository userRedpository;

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
    public ResponseEntity<UserResponseDTO> getCurrentUser(@AuthenticationPrincipal String email) {
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userRedpository.findByEmail(email)
                .orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(toUserResponse(user));
    }

    private UserResponseDTO toUserResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}
