package org.example.healthcare.mapper;


import org.example.healthcare.dto.medecin.MedecinRequestDto;
import org.example.healthcare.dto.medecin.MedecinResponseDto;
import org.example.healthcare.model.Medecin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedecinMapper {
    Medecin toEntity(MedecinRequestDto requestDto);
    MedecinResponseDto toDto(Medecin medecin);
    List<MedecinResponseDto> toListDto(List<Medecin> medecins);
}
