package org.example.healthcare.mapper;


import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.model.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Patient toEntity(PatientRequestDto requestDto);
    PatientRespenseDto toDto(Patient patient);
    List<PatientRespenseDto> toListDto(List<Patient> patients);
}
