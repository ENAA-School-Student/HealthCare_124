package org.example.healthcare.controller;

import jakarta.validation.Valid;
import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.model.User;
import org.example.healthcare.repository.UserRedpository;
import org.example.healthcare.security.SecurityUtils;
import org.example.healthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    UserRedpository userRedpository;

    @GetMapping
    public List<PatientRespenseDto> consulterTous() {
        return patientService.consulterTous();
    }

    @PostMapping
    public ResponseEntity<PatientRespenseDto> ajouter(@Valid @RequestBody PatientRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.ajouter(requestDto));
    }

    @GetMapping("/{id}")
    public PatientRespenseDto consulter(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        SecurityUtils.requireOwnerOrAdmin(id, current);
        return patientService.consulter(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientRespenseDto> modifier(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDto patientRequestDto
    ) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        SecurityUtils.requireOwnerOrAdmin(id, current);
        return ResponseEntity.ok(patientService.modifier(id, patientRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        if (!SecurityUtils.isAdmin(current)) {
            throw new AccessDeniedException("Seul un administrateur peut supprimer un patient.");
        }
        patientService.supprimer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recherche/nom")
    public Page<PatientRespenseDto> chercherPatientParNomPagine(
            @RequestParam String nom,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return patientService.chercherPatientParNom(nom, page, size);
    }

    @GetMapping("/sorting")
    public Page<PatientRespenseDto> sorting(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortField,
            @RequestParam String sortDirection
    ) {
        return patientService.consulterPatientTriparnom(sortField, sortDirection, page, size);
    }
}
