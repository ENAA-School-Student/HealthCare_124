package org.example.healthcare.controller;

import jakarta.validation.Valid;
import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.enums.RendezVousStatut;
import org.example.healthcare.enums.UserRoles;
import org.example.healthcare.model.RendezVous;
import org.example.healthcare.model.User;
import org.example.healthcare.repository.UserRedpository;
import org.example.healthcare.security.SecurityUtils;
import org.example.healthcare.service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private UserRedpository userRedpository;

    @PostMapping
    public ResponseEntity<RendezVousResponseDto> ajouter(@Valid @RequestBody RendezVousRequestDto requestDto) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        if (current.getRole() == UserRoles.PATIENT && !requestDto.getPatientId().equals(current.getId())) {
            throw new AccessDeniedException("Un patient ne peut créer un rendez-vous que pour lui-même.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(rendezVousService.ajouter(requestDto));
    }

    @GetMapping
    public List<RendezVousResponseDto> consulterTous() {
        return rendezVousService.consulterTous();
    }

    @GetMapping("/{id}")
    public RendezVousResponseDto consulter(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        RendezVous rdv = rendezVousService.findById(id);
        requireRendezVousAccess(rdv, current);
        return rendezVousService.consulter(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVousResponseDto> modifier(
            @PathVariable Long id,
            @Valid @RequestBody RendezVousRequestDto requestDto
    ) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        RendezVous rdv = rendezVousService.findById(id);
        requireRendezVousAccess(rdv, current);
        return ResponseEntity.ok(rendezVousService.modifier(id, requestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> modifierStatut(@PathVariable Long id, @RequestParam RendezVousStatut statut) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        RendezVous rdv = rendezVousService.findById(id);
        requireRendezVousAccess(rdv, current);
        rendezVousService.modifierRendezVousStatut(id, statut);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        RendezVous rdv = rendezVousService.findById(id);
        requireRendezVousAccess(rdv, current);
        rendezVousService.supprimer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient/{id}")
    public List<RendezVousResponseDto> chercherParPatient(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        if (current.getRole() == UserRoles.PATIENT) {
            SecurityUtils.requireOwnerOrAdmin(id, current);
        }
        return rendezVousService.chercherRebdezVousParPatient(id);
    }

    @GetMapping("/medecin/{id}")
    public List<RendezVousResponseDto> chercherParMedecin(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        if (current.getRole() == UserRoles.MEDECIN) {
            SecurityUtils.requireOwnerOrAdmin(id, current);
        }
        return rendezVousService.chercherRebdezVousParMedecin(id);
    }

    @GetMapping("/pagines")
    public Page<RendezVousResponseDto> consulterRendezVousPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return rendezVousService.consulterRenderVousTriPAreDate(page, size);
    }

    @GetMapping("/tri/date")
    public Page<RendezVousResponseDto> consulterRendezVousTriParDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return rendezVousService.consulterRenderVousTriPAreDate(page, size);
    }

    @GetMapping("/recherche/statut")
    public Page<RendezVousResponseDto> chercherRendezVousParStatutPagine(
            @RequestParam RendezVousStatut statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return rendezVousService.consulterRendervousparStatut(statut, page, size);
    }

    private void requireRendezVousAccess(RendezVous rdv, User current) {
        if (SecurityUtils.isAdmin(current)) return;
        boolean isPatientOwner = current.getRole() == UserRoles.PATIENT
                && rdv.getPatient() != null
                && rdv.getPatient().getId().equals(current.getId());
        boolean isMedecinOwner = current.getRole() == UserRoles.MEDECIN
                && rdv.getMedecin() != null
                && rdv.getMedecin().getId().equals(current.getId());
        if (!isPatientOwner && !isMedecinOwner) {
            throw new AccessDeniedException("Accès refusé");
        }
    }
}
