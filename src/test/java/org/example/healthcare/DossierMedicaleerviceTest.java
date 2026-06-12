package org.example.healthcare;


import jakarta.transaction.Transactional;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleRequestDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleResponseDto;
import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.service.DossierMedicaleService;
import org.example.healthcare.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DossierMedicaleerviceTest {

    @Autowired
    private DossierMedicaleService dossierMedicaleService;

    @Autowired
    private PatientService patientService;

    private PatientRequestDto patientRequestDto;

    @BeforeEach
    public void setup() {
        patientRequestDto = new PatientRequestDto(
                "Alami",
                "Ahmed",
                "patientTest@mail.com",
                "+212600000000",
                LocalDate.parse("2000-10-10")
        );
    }

    @Test
    @DisplayName("Test de créer un dossier médical")
    public void creerDossierMedicleTest() {
        PatientRespenseDto patientSaved = patientService.ajouter(patientRequestDto);

        DossierMedicaleRequestDto dossierRequest = new DossierMedicaleRequestDto(
                LocalDateTime.now(),
                patientSaved.getId()
        );

        DossierMedicaleResponseDto response = dossierMedicaleService.ajouter(dossierRequest);

        assertEquals(patientSaved.getEmail(), response.getPatient().getEmail());
        assertEquals(patientSaved.getId(), response.getPatient().getId());
    }



    @Test
    @DisplayName("Test de créer un dossier médical cas de patient a deja un dossier medicale")
    public void creerDossierMedicleTest2(){
        PatientRespenseDto patientSaved = patientService.ajouter(patientRequestDto);

        DossierMedicaleRequestDto dossierRequest = new DossierMedicaleRequestDto(
                LocalDateTime.now(),
                patientSaved.getId()
        );
        dossierMedicaleService.ajouter(dossierRequest);


        assertThrows(IllegalStateException.class,()->{
            dossierMedicaleService.ajouter(dossierRequest);
        });
    }

    @Test
    @DisplayName("Test d'exportation d'un dossier médical en PDF")
    public void exportPdfTest() {
        PatientRespenseDto patientSaved = patientService.ajouter(patientRequestDto);
        DossierMedicaleRequestDto dossierRequest = new DossierMedicaleRequestDto(
                LocalDateTime.now(),
                patientSaved.getId()
        );
        DossierMedicaleResponseDto response = dossierMedicaleService.ajouter(dossierRequest);

        ByteArrayInputStream bis = dossierMedicaleService.exportPdf(response.getId());
        assertNotNull(bis);
    }
}