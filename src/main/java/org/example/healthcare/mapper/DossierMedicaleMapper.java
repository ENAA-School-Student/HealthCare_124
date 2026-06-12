package org.example.healthcare.mapper;


import org.example.healthcare.dto.dossierMedicale.DossierMedicaleRequestDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleResponseDto;
import org.example.healthcare.model.DossierMedicale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;



@Mapper(componentModel = "spring", uses = {ConsultationMapper.class})
public interface DossierMedicaleMapper {



    @Mapping(target = "patient" , ignore = true)
    DossierMedicale toEnity(DossierMedicaleRequestDto dto);


    DossierMedicaleResponseDto toDto(DossierMedicale dossierMedicale);


    List<DossierMedicaleResponseDto> toListDto(List<DossierMedicale> dossierMedicales);


}
