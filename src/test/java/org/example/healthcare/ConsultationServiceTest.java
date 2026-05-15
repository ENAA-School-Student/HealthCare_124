package org.example.healthcare;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.healthcare.dto.consultation.ConsultationRequestDto;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleRequestDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleResponseDto;
import org.example.healthcare.dto.medecin.MedecinRequestDto;
import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.service.ConsultationService;
import org.example.healthcare.service.DossierMedicaleService;
import org.example.healthcare.service.MedecinService;
import org.example.healthcare.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ConsultationServiceTest {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DossierMedicaleService dossierMedicaleService;

    @Autowired
    private MedecinService medecinService;

    private Long savedDossierId;

    private Long savedMedecinId;


    @BeforeEach
    public void setup() {
        PatientRequestDto patientReq = new PatientRequestDto("Alami", "Ahmed", "ahmed@mail.com", "+212600000000", LocalDate.of(1990, 1, 1));
        PatientRespenseDto patient = patientService.ajouter(patientReq);

        DossierMedicaleRequestDto dossierReq = new DossierMedicaleRequestDto(LocalDateTime.now(), patient.getId());
        DossierMedicaleResponseDto dossier = dossierMedicaleService.ajouter(dossierReq);
        savedDossierId = dossier.getId();

        MedecinRequestDto medecin = new MedecinRequestDto("Dr. Idrissi", "Sami", "Généraliste");
        savedMedecinId = medecinService.ajouter(medecin).getId();
    }

    @Test
    @DisplayName("Doit ajouter une consultation avec succès")
    public void shouldAddConsultationSuccessfully() {
        ConsultationRequestDto requestDto = new ConsultationRequestDto(
                "Grippe saisonnière",
                "Repos et paracétamol",
                LocalDateTime.now(),
                savedDossierId,
                savedMedecinId
        );

        ConsultationResponseDto response = consultationService.ajouter(requestDto);

        assertNotNull(response.getId());
        assertEquals("Grippe saisonnière", response.getDiagnostic());
        assertEquals(savedMedecinId, response.getMedecin().getId());
    }

    @Test
    @DisplayName("Doit échouer si le dossier médical n'existe pas")
    public void shouldThrowExceptionWhenDossierNotFound() {
        ConsultationRequestDto requestWithBadDossier = new ConsultationRequestDto(
                "Test", "Test", LocalDateTime.now(), 999L, savedMedecinId
        );

        assertThrows(EntityNotFoundException.class, () -> {
            consultationService.ajouter(requestWithBadDossier);
        });
    }

}
