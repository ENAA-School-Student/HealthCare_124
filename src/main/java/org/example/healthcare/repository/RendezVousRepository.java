package org.example.healthcare.repository;

import org.example.healthcare.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous , Long> {

    boolean existsByMedecinIdAndDateRendezVous(Long medecinId, LocalDateTime dateRendezVous);


    List<RendezVous> findRendezVousByPatientId(Long patientId);

    List<RendezVous>  findRendezVousByMedecinId(Long medecinId);
}
