package org.example.healthcare.controller;

import jakarta.validation.Valid;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleRequestDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleResponseDto;
import org.example.healthcare.enums.UserRoles;
import org.example.healthcare.model.DossierMedicale;
import org.example.healthcare.model.User;
import org.example.healthcare.repository.UserRedpository;
import org.example.healthcare.security.SecurityUtils;
import org.example.healthcare.service.DossierMedicaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dossier_medicale")
public class DossierMedicaleController {

    @Autowired
    private DossierMedicaleService dossierMedicaleService;

    @Autowired
    private UserRedpository userRedpository;

    @GetMapping
    public List<DossierMedicaleResponseDto> consulterTous() {
        return dossierMedicaleService.consulterTous();
    }

    @GetMapping("/{id}")
    public DossierMedicaleResponseDto consulter(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        if (current.getRole() == UserRoles.PATIENT) {
            DossierMedicale dossier = dossierMedicaleService.findById(id);
            if (dossier.getPatient() == null || !dossier.getPatient().getId().equals(current.getId())) {
                throw new AccessDeniedException("Accès refusé");
            }
        }
        return dossierMedicaleService.consulter(id);
    }

    @GetMapping("/patient/{id}")
    public DossierMedicaleResponseDto chercherDossierParPatient(@PathVariable Long id) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        if (current.getRole() == UserRoles.PATIENT) {
            SecurityUtils.requireOwnerOrAdmin(id, current);
        }
        return dossierMedicaleService.consulterParPatient(id);
    }

    @GetMapping("/consultations/{id_dossier}")
    public List<ConsultationResponseDto> consulterDossierConsultation(@PathVariable("id_dossier") Long dossierId) {
        User current = SecurityUtils.getCurrentUser(userRedpository);
        if (current.getRole() == UserRoles.PATIENT) {
            DossierMedicale dossier = dossierMedicaleService.findById(dossierId);
            if (dossier.getPatient() == null || !dossier.getPatient().getId().equals(current.getId())) {
                throw new AccessDeniedException("Accès refusé");
            }
        }
        return dossierMedicaleService.consulterDossierConsultations(dossierId);
    }

    @PostMapping
    public ResponseEntity<DossierMedicaleResponseDto> ajouter(@Valid @RequestBody DossierMedicaleRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dossierMedicaleService.ajouter(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DossierMedicaleResponseDto> modifier(
            @PathVariable Long id,
            @Valid @RequestBody DossierMedicaleRequestDto requestDto
    ) {
        return ResponseEntity.ok(dossierMedicaleService.modifier(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        dossierMedicaleService.supprimer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagines")
    public Page<DossierMedicaleResponseDto> consulterDossiersPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return dossierMedicaleService.consulterTousPagine(page, size);
    }
}
