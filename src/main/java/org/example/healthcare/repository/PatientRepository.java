package org.example.healthcare.repository;

import org.example.healthcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient , Long> {
}
