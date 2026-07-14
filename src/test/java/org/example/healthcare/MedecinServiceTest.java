//package org.example.healthcare;
//
//
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import org.example.healthcare.dto.medecin.MedecinRequestDto;
//import org.example.healthcare.dto.medecin.MedecinResponseDto;
//import org.example.healthcare.mapper.MedecinMapper;
//import org.example.healthcare.repository.MedecinRepository;
//import org.example.healthcare.service.MedecinService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
//
//@SpringBootTest
//@Transactional
//public class MedecinServiceTest {
//
//    @Autowired
//    private MedecinService medecinService;
//
//
//    @Test
//    @DisplayName("Test de modification cas normale")
//    public void medcinsModifierTest(){
//        MedecinRequestDto medecinRequest = new MedecinRequestDto(
//                "Ouazzani",
//                "Driss",
//                "Cardiologie"
//        );
//
//        MedecinRequestDto medecinRequest_m = new MedecinRequestDto(
//                "Ouazzani",
//                "Driss",
//                "Cardiologie"
//        );
//
//        MedecinResponseDto responseDto = medecinService.ajouter(medecinRequest);
//
//        MedecinResponseDto responseDto_m = medecinService.modifier(responseDto.getId(),medecinRequest_m);
//
//        Assertions.assertEquals("dr.chetouni@healthcare.ma",responseDto_m.getEmail());
//
//    }
//
//    @Test
//    @DisplayName("Test de modification cas de medecin n'exist pas")
//    public void medcinsModifierTest2(){
//        MedecinRequestDto medecinRequest_m = new MedecinRequestDto(
//                "Ouazzani",
//                "Driss",
//                "Cardiologie"
//        );
//        Assertions.assertThrows(EntityNotFoundException.class,()->{
//            medecinService.modifier(1000L,medecinRequest_m);
//        });
//    }
//
//
//
//
//
//}
