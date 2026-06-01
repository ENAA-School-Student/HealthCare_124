package org.example.healthcare.repository;

import org.example.healthcare.model.DossierMedicale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierMedicaleRepository extends JpaRepository<DossierMedicale , Long> {
    DossierMedicale findDossierMedicaleByPatient_Nom(String patientNom);

    DossierMedicale findDossierMedicaleByPatient_Id(Long patientId);

    boolean existsByPatient_Id(Long patientId);

    Page<DossierMedicale> findAll(Pageable pageable);

}
