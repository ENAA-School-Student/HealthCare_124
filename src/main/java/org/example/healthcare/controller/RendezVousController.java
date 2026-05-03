package org.example.healthcare.controller;


import jakarta.validation.Valid;
import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.enums.RendezVousStatut;
import org.example.healthcare.service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @PostMapping
    public ResponseEntity<RendezVousResponseDto> ajouter(
            @Valid @RequestBody RendezVousRequestDto requestDto
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(rendezVousService.ajouter(requestDto));
    }

    @GetMapping
    public List<RendezVousResponseDto> consulterTous(){
        return rendezVousService.consulterTous();
    }

    @GetMapping("/{id}")
    public RendezVousResponseDto consulter(Long id){
        return rendezVousService.consulter(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVousResponseDto> modifier(
            @PathVariable Long id,
            @Valid @RequestBody RendezVousRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(rendezVousService.modifier(id,requestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> modifierStatut(@PathVariable Long id, @RequestParam RendezVousStatut statut){
        rendezVousService.modifierRendezVousStatut(id,statut);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id){
        rendezVousService.supprimer(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/patient/{id}")
    public List<RendezVousResponseDto> chercherParPatient(@PathVariable Long id){
        return rendezVousService.chercherRebdezVousParPatient(id);
    }


    @GetMapping("/medecin/{id}")
    public List<RendezVousResponseDto> chercherParMedecin(@PathVariable Long id){
        return rendezVousService.chercherRebdezVousParMedecin(id);
    }
}
