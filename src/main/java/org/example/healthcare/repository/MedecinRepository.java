package org.example.healthcare.repository;

import org.example.healthcare.model.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin , Long> {



    @Query("SELECT m FROM Medecin m JOIN m.rendezVous r WHERE r.patient.id =:id")
    List<Medecin> recupererLesmedcindunpatient(@Param("id") Long id);



    Page<Medecin> findAllByOrderBySpecialiteAsc(Pageable pageable);

    Page<Medecin> findAllBySpecialite(String specialite,Pageable pageable);


}
