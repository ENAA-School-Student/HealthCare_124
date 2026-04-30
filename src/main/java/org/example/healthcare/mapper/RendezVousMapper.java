package org.example.healthcare.mapper;


import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.model.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {


    @Mapping(target = "patient" ,ignore = true)
    @Mapping(target = "medecin" ,ignore = true)
    RendezVous toEntity(RendezVousRequestDto dto);
    RendezVousResponseDto toDto(RendezVous rendezVous);
    List<RendezVousResponseDto> toListDto(  List<RendezVous> rendezVous);
}
