package org.example.healthcare.controller;


import jakarta.validation.Valid;
import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping
    public List<PatientRespenseDto> consulterTous(){
        return patientService.consulterTous();
    }

    @PostMapping
    public ResponseEntity<PatientRespenseDto> ajouter(
           @Valid @RequestBody PatientRequestDto requestDto
            ){
        PatientRespenseDto patientRespenseDto = patientService.ajouter(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientRespenseDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public PatientRespenseDto consulter(
            @PathVariable Long id
    ){
        return patientService.consulter(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientRespenseDto> modifier(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDto patientRequestDto
    ){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.modifier(id,patientRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ){
        patientService.supprimer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagines")
    public Page<PatientRespenseDto> consulterPatientsPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return patientService.consulterPatientTriparnom("", page, size);
    }

    @GetMapping("/tri/nom")
    public Page<PatientRespenseDto> consulterPatientTriParNom(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return patientService.consulterPatientTriparnom("", page, size);
    }

    @GetMapping("/recherche/nom")
    public Page<PatientRespenseDto> chercherPatientParNomPagine(
            @RequestParam String nom,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return patientService.chercherPatientParNom(nom, page, size);
    }

}
