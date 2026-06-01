package org.example.healthcare.controller;


import jakarta.validation.Valid;
import org.example.healthcare.dto.medecin.MedecinRequestDto;
import org.example.healthcare.dto.medecin.MedecinResponseDto;
import org.example.healthcare.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medecin")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;

    @GetMapping
    public List<MedecinResponseDto> consulterTous(){
        return medecinService.consulterTous();
    }

    @PostMapping
    public ResponseEntity<MedecinResponseDto> ajouter(
            @Valid @RequestBody MedecinRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(medecinService.ajouter(requestDto));
    }


    @GetMapping("/{id}")
    public MedecinResponseDto consulter(
            @PathVariable Long id
    ){
        return medecinService.consulter(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedecinResponseDto> modifier(
            @PathVariable Long id,
            @Valid @RequestBody MedecinRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(medecinService.modifier(id,requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ){
        medecinService.supprimer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagines")
    public Page<MedecinResponseDto> consulterMedecinsPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return medecinService.consulterMedecinTriparSpecialite(page, size);
    }

    @GetMapping("/tri/specialite")
    public Page<MedecinResponseDto> consulterMedecinTriParSpecialite(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return medecinService.consulterMedecinTriparSpecialite(page, size);
    }

    @GetMapping("/recherche/specialite")
    public Page<MedecinResponseDto> chercherMedecinParSpecialitePagine(
            @RequestParam String specialite,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return medecinService.chercherMedecinParSpecialite(specialite, page, size);
    }

}
