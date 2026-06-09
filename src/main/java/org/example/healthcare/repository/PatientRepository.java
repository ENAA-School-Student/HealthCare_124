package org.example.healthcare.repository;

import org.example.healthcare.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient , Long> {


    

    Page<Patient> findAllByOrderByNom(Pageable pageable);

    Page<Patient> findAllByOrderByNomAsc(Pageable pageable);


    Page<Patient> findAllByNom(String nom,Pageable pageable);



}
