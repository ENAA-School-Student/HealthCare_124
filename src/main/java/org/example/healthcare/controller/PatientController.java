package org.example.healthcare.controller;


import jakarta.validation.Valid;
import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.model.Patient;
import org.example.healthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.modifier(id,patientRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ){
        return ResponseEntity.ok().build();
    }

}
