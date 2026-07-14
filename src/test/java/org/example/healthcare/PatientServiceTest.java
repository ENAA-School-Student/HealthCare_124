//package org.example.healthcare;
//
//
//import jakarta.transaction.Transactional;
//import org.example.healthcare.dto.patient.PatientRequestDto;
//import org.example.healthcare.dto.patient.PatientRespenseDto;
//import org.example.healthcare.service.PatientService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class PatientServiceTest {
//
//    @Autowired
//    private PatientService patientService;
//
//
//    @Test
//    @DisplayName("Test d'ajouter patient")
//    public void ajouterTest() {
//        PatientRequestDto requestDto = new PatientRequestDto(
//                "patient",
//                "patient",
//                "patienttest55@mail.com",
//                "292939387",
//                LocalDate.parse("2000-10-10")
//        );
//
//        PatientRespenseDto respenseDto = patientService.ajouter(requestDto);
//        assertNotNull(respenseDto.getId());
//        assertEquals("patienttest55@mail.com", respenseDto.getEmail());
//    }
//
//
//
//
//    @Test
//    @DisplayName("Test d'ajouter patient cas d'email existant")
//    public void ajouterTest2() {
//        PatientRequestDto requestDto = new PatientRequestDto(
//                "patient",
//                "patient",
//                "patienttest2@mail.com",
//                "292939387",
//                LocalDate.parse("2000-10-10")
//        );
//        patientService.ajouter(requestDto);
//        PatientRequestDto requestDto2 = new PatientRequestDto(
//                "patient2",
//                "patient2",
//                "patienttest2@mail.com",
//                "2929393827",
//                LocalDate.parse("2000-10-10")
//        );
//        assertThrows(Exception.class, () -> {
//            patientService.ajouter(requestDto2);
//        });
//
//    }
//
//}
