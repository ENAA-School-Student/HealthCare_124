package org.example.healthcare;

import org.example.healthcare.model.Patient;
import org.example.healthcare.model.RendezVous;
import org.example.healthcare.repository.MedecinRepository;
import org.example.healthcare.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ChellengesTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Test
    void test_1(){
        LocalDateTime time = LocalDateTime.parse("2026-05-20T09:00:00");

        List<RendezVous>  rendezVous= medecinRepository.recupererLesRendezVousPardateJPQ(time);
        System.out.println(rendezVous.get(0).getStatut());

        List<RendezVous>  rendezVous1 = medecinRepository.recupererLesRendezVousPardateSQL(time);
        System.out.println(rendezVous1.get(0).getStatut());
    }


    @Test
    void test_2(){
        String nom = "Benali";

        List<RendezVous> rendezVous = patientRepository.rendezVousParPAtient(nom);
        System.out.println(rendezVous.getFirst().getStatut());

        List<RendezVous> rendezVous1 = patientRepository.rendezVousParPAtientNative(nom);
        System.out.println(rendezVous1.getFirst().getStatut());

    }

    @Test
    void test_3(){
        List<Patient> patients = patientRepository.patientHaveMoreThenX(1);
        System.out.println(patients.getFirst().getNom());
        List<Patient> patients_2 = patientRepository.patientHaveMoreThenXnative(1);
        System.out.println(patients_2.getFirst().getNom());
    }
}