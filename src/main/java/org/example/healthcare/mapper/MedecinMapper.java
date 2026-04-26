package org.example.healthcare.mapper;


import org.example.healthcare.dto.MedecinRequestDto;
import org.example.healthcare.dto.MedecinResponseDto;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.model.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedecinMapper {
    Medecin toEntity(MedecinRequestDto requestDto);
    MedecinResponseDto toDto(Medecin medecin);
    List<MedecinResponseDto> toLsitDto(List<Patient> patients);
}
