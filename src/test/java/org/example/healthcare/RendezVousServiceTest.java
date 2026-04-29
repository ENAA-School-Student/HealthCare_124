package org.example.healthcare;


import jakarta.transaction.Transactional;
import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.enums.RendezVousStatut;
import org.example.healthcare.mapper.PatientMapper;
import org.example.healthcare.model.Patient;
import org.example.healthcare.service.PatientService;
import org.example.healthcare.service.RendezVousService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class RendezVousServiceTest {

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientMapper patientMapper;

    @Test
    @DisplayName("1 : Test d'ajouter un rendez-vous")
    public void test(){
        RendezVousRequestDto requestDto = new RendezVousRequestDto(
                LocalDateTime.now(),
                RendezVousStatut.PLANIFIE,
                1L,
                1L
        );


        PatientRespenseDto patient =  patientService.consulter(1L);

        RendezVousResponseDto response = rendezVousService.ajouter(requestDto);

        assertEquals(patient.getEmail(),response.getPatient().getEmail());
    }

}
