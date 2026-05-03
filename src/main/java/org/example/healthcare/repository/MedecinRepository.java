package org.example.healthcare.repository;

import org.example.healthcare.model.Medecin;
import org.example.healthcare.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin , Long> {


    @Query("SELECT rv FROM RendezVous rv JOIN rv.medecin m WHERE rv.dateRendezVous =:date")
    List<RendezVous> recupererLesRendezVousPardateJPQ(LocalDateTime date);


    @Query(value = "SELECT rendez_vous.* FROM rendez_vous JOIN medecins ON rendez_vous.medecin_id = medecins.id WHERE rendez_vous.date_rendez_vous =:date",nativeQuery = true)
    List<RendezVous> recupererLesRendezVousPardateSQL(@Param("date") LocalDateTime date);



}
