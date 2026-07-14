package org.example.healthcare.mapper;


import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.model.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "medecin", ignore = true)
    RendezVous toEntity(RendezVousRequestDto dto);

    @Mapping(target = "patientId",     source = "patient.id")
    @Mapping(target = "patientNom",    source = "patient.nom")
    @Mapping(target = "patientPrenom", source = "patient.prenom")
    @Mapping(target = "medecinId",     source = "medecin.id")
    @Mapping(target = "medecinNom",    source = "medecin.nom")
    @Mapping(target = "medecinPrenom", source = "medecin.prenom")
    RendezVousResponseDto toDto(RendezVous rendezVous);

    List<RendezVousResponseDto> toListDto(List<RendezVous> rendezVous);
}
