package org.example.healthcare.repository;

import org.example.healthcare.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RendezVousRepository extends JpaRepository<RendezVous , Long> {

    boolean existsByMedecinIdAndDateRendezVous(Long medecinId, LocalDateTime dateRendezVous);


    RendezVous findRendezVousByPatientId(Long patientId);

    RendezVous findRendezVousByMedecinId(Long medecinId);
}
