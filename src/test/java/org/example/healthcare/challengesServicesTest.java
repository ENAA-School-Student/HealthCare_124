package org.example.healthcare;


import org.example.healthcare.dto.medecin.ChallengeResponseDto;
import org.example.healthcare.service.MedecinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class challengesServicesTest {

    @Autowired
    MedecinService medecinService;

    @Test
    void test(){
        List<ChallengeResponseDto> list = medecinService.consulterTousAvecNombreDesRendezVous();

        System.out.println(list.getFirst().getNom() +" : "+ list.getFirst().getCountRendezvous());
    }
}
