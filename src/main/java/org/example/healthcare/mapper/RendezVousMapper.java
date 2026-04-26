package org.example.healthcare.mapper;


import org.example.healthcare.dto.RendezVousRequestDto;
import org.example.healthcare.dto.RendezVousResponseDto;
import org.example.healthcare.model.RendezVous;
import org.example.healthcare.repository.MedecinRepository;
import org.example.healthcare.repository.PatientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring" ,uses = {PatientRepository.class, MedecinRepository.class})
public interface RendezVousMapper {


    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "medecin", source = "medecinId")
    RendezVous toEntity(RendezVousRequestDto dto);

    @Mapping(target = "patientNom", source = "patient.nom")
    @Mapping(target = "medecinNom", source = "medecin.nom")
    RendezVousResponseDto toDto(RendezVous rendezVous);


    List<RendezVous> toListDto(List<RendezVousResponseDto> rendezVousResponseDtos);
}
