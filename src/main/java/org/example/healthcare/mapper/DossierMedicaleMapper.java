package org.example.healthcare.mapper;


import org.example.healthcare.dto.DossierMedicaleRequestDto;
import org.example.healthcare.dto.DossierMedicaleResponseDto;
import org.example.healthcare.model.DossierMedicale;
import org.example.healthcare.repository.MedecinRepository;
import org.example.healthcare.repository.PatientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;



@Mapper(componentModel = "spring" , uses = {PatientRepository.class, MedecinRepository.class})
public interface DossierMedicaleMapper {



    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "medecin" ,source = "medecinId")
    DossierMedicale toEnity(DossierMedicaleRequestDto dto);


    @Mapping(target = "patientNom", source = "patient.nom")
    @Mapping(target = "medecinNom", source = "medecin.nom")
    DossierMedicaleResponseDto toDto(DossierMedicale dossierMedicale);


    List<DossierMedicaleResponseDto> toListDto(List<DossierMedicale> dossierMedicales);


}
