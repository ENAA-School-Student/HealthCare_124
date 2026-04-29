package org.example.healthcare.controller;


import jakarta.validation.Valid;
import org.example.healthcare.dto.consultation.ConsultationRequestDto;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;


    @PostMapping
    public ResponseEntity<ConsultationResponseDto> ajouter(
            @Valid @RequestBody ConsultationRequestDto requestDto
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(consultationService.ajouter(requestDto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationResponseDto> modifier(
            @PathVariable Long id,
            @Valid @RequestBody ConsultationRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(consultationService.modifier(id,requestDto));
    }

}
