package org.example.healthcare.mapper;


import org.example.healthcare.dto.consultation.ConsultationRequestDto;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.model.Consultation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    Consultation toEntity(ConsultationRequestDto requestDto);

    ConsultationResponseDto ToDto(Consultation consultation);

    List<ConsultationResponseDto> toListDto(List<Consultation> consultations);


}
