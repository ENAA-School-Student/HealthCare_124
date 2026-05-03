package org.example.healthcare.repository;

import org.example.healthcare.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous , Long> {

    boolean existsByMedecinIdAndDateRendezVous(Long medecinId, LocalDateTime dateRendezVous);


    List<RendezVous> findRendezVousByPatientId(Long patientId);

    List<RendezVous>  findRendezVousByMedecinId(Long medecinId);

    @Query("SELECT rv FROM RendezVous rv JOIN rv.medecin m WHERE m.nom =:nom")
    List<RendezVous> renderVousParNomMedecin(String nom);

    Long countRendezVousByMedecinId(Long id);
}
