package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.healthcare.dto.medecin.MedecinRequestDto;
import org.example.healthcare.dto.medecin.MedecinResponseDto;
import org.example.healthcare.mapper.MedecinMapper;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {
    @Autowired
    private MedecinRepository medecinRepository;


    @Autowired
    private MedecinMapper medecinMapper;


    @Transactional
    public MedecinResponseDto ajouter(MedecinRequestDto requestDto){
        Medecin medecin = medecinMapper.toEntity(requestDto);
        Medecin medecin_saved = medecinRepository.save(medecin);
        return medecinMapper.toDto(medecin_saved);
    }

    public List<MedecinResponseDto> consulterTous(){
        return medecinMapper.toLsitDto(medecinRepository.findAll());
    }

    public MedecinResponseDto consulter(Long id){
        return medecinMapper.toDto(medecinRepository.findById(id).orElse(null));
    }
    @Transactional
    public  MedecinResponseDto modifier(Long id,MedecinRequestDto requestDto){
        if (!medecinRepository.existsById(id)){
            throw new EntityNotFoundException("Medecin introuvable");
        }
        Medecin medecin_a_modifier = medecinMapper.toEntity(requestDto);
        medecin_a_modifier.setId(id);
        return medecinMapper.toDto(medecinRepository.save(medecin_a_modifier));
    }

    @Transactional
    public void supprimer(Long id){
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medecin introuvable"));
        medecinRepository.delete(medecin);
    }
}
