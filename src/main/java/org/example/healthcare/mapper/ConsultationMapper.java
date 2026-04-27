package org.example.healthcare.mapper;


import org.example.healthcare.dto.consultation.ConsultationRequestDto;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.model.Consultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {


    @Mapping(target = "medecin" ,ignore = true)
    @Mapping(target = "dossier" ,ignore = true)
    Consultation toEntity(ConsultationRequestDto requestDto);

    ConsultationResponseDto toDto(Consultation consultation);

    List<ConsultationResponseDto> toListDto(List<Consultation> consultations);


}
