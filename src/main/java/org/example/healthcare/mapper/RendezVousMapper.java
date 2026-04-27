package org.example.healthcare.mapper;


import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.model.RendezVous;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {

    RendezVous toEntity(RendezVousRequestDto dto);

    RendezVousResponseDto toDto(RendezVous rendezVous);


    List<RendezVous> toListDto(List<RendezVousResponseDto> rendezVousResponseDtos);
}
