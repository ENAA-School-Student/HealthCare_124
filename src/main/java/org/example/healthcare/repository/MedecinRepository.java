package org.example.healthcare.repository;

import org.example.healthcare.model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin , Long> {
}
