package org.example.healthcare.controller;

import jakarta.validation.Valid;
import org.example.healthcare.dto.consultation.ConsultationRequestDto;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.model.Consultation;
import org.example.healthcare.model.User;
import org.example.healthcare.repository.UserRedpository;
import org.example.healthcare.security.SecurityUtils;
import org.example.healthcare.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private UserRedpository userRedpository;

    @PostMapping
    public ResponseEntity<ConsultationResponseDto> ajouter(@Valid @RequestBody ConsultationRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultationService.ajouter(requestDto));
    }

    @GetMapping("/{id}")
    public ConsultationResponseDto consulter(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        requireConsultationAccess(id, current);
        return consultationService.consulter(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationResponseDto> modifier(
            @PathVariable Long id,
            @Valid @RequestBody ConsultationRequestDto requestDto
    ) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        requireConsultationAccess(id, current);
        return ResponseEntity.ok(consultationService.modifier(id, requestDto));
    }

    @GetMapping
    public List<ConsultationResponseDto> consulterTous() {
        return consultationService.consulterTous();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        requireConsultationAccess(id, current);
        consultationService.supprimer(id);
        return ResponseEntity.ok().build();
    }

    private void requireConsultationAccess(Long id, User current) {
        if (SecurityUtils.isAdmin(current)) return;
        Consultation consultation = consultationService.findById(id);
        if (consultation.getMedecin() == null || !consultation.getMedecin().getId().equals(current.getId())) {
            throw new AccessDeniedException("Accès refusé");
        }
    }
}
