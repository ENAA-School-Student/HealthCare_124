package org.example.healthcare.repository;

import org.example.healthcare.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation , Long> {
}
