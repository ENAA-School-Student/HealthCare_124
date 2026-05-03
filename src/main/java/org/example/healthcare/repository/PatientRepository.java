package org.example.healthcare.repository;

import jakarta.transaction.Transactional;
import org.example.healthcare.model.Patient;
import org.example.healthcare.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient , Long> {


    @Query("SELECT rv FROM RendezVous rv JOIN rv.patient p WHERE p.nom =:nom")
    List<RendezVous> rendezVousParPAtient(String nom);


    @Query(value = "SELECT rendez_vous.* FROM rendez_vous JOIN patients ON rendez_vous.patient_id = patients.id WHERE patients.nom =:nom",nativeQuery = true)
    List<RendezVous> rendezVousParPAtientNative(@Param("nom") String nom);

    @Query("SELECT p FROM Patient p JOIN p.rendezVous r GROUP BY p.id HAVING COUNT(r.id) > :x")
    List<Patient> patientHaveMoreThenX(@Param("x") long x);

@Transactional
    @Query(value = "SELECT patients.* FROM patients JOIN rendez_vous ON patients.id = rendez_vous.patient_id GROUP BY (patients.id) HAVING COUNT(patients.id) > :x" , nativeQuery = true)
    List<Patient> patientHaveMoreThenXnative(@Param("x") long x);


}
