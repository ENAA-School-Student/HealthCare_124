package org.example.healthcare.mapper;


import org.example.healthcare.dto.dossierMedicale.DossierMedicaleRequestDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleResponseDto;
import org.example.healthcare.model.DossierMedicale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DossierMedicaleMapper {

    @Mapping(target = "patient", ignore = true)
    DossierMedicale toEntity(DossierMedicaleRequestDto dto);

    @Mapping(target = "patientId",     source = "patient.id")
    @Mapping(target = "patientNom",    source = "patient.nom")
    @Mapping(target = "patientPrenom", source = "patient.prenom")
    DossierMedicaleResponseDto toDto(DossierMedicale dossierMedicale);

    List<DossierMedicaleResponseDto> toListDto(List<DossierMedicale> dossierMedicales);
}
