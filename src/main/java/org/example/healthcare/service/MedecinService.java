package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.example.healthcare.dto.medecin.MedecinRequestDto;
import org.example.healthcare.dto.medecin.MedecinResponseDto;
import org.example.healthcare.mapper.MedecinMapper;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {
    @Autowired
    private MedecinRepository medecinRepository;


    @Autowired
    private MedecinMapper medecinMapper;


    @Transactional
    @CacheEvict(value = {"medecins", "medecins-pages"}, allEntries = true)
    public MedecinResponseDto ajouter(MedecinRequestDto requestDto){
        Medecin medecin = medecinMapper.toEntity(requestDto);
        Medecin medecin_saved = medecinRepository.save(medecin);
        return medecinMapper.toDto(medecin_saved);
    }


    @Cacheable(value = "medecins",key = "'all'")
    public List<MedecinResponseDto> consulterTous(){
        return medecinMapper.toLsitDto(medecinRepository.findAll());
    }

    @Cacheable(value = "medecins",key = "'single-' + #id")
    public MedecinResponseDto consulter(Long id){
        return medecinMapper.toDto(medecinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medecin introuvable")));
    }
    @Transactional
    @CacheEvict(value = {"medecins", "medecins-pages"}, allEntries = true)
    public  MedecinResponseDto modifier(Long id,MedecinRequestDto requestDto){
        if (!medecinRepository.existsById(id)){
            throw new EntityNotFoundException("Medecin introuvable");
        }
        Medecin medecin_a_modifier = medecinMapper.toEntity(requestDto);
        medecin_a_modifier.setId(id);
        return medecinMapper.toDto(medecinRepository.save(medecin_a_modifier));
    }

    @Transactional
    @CacheEvict(value = {"medecins", "medecins-pages"}, allEntries = true)
    public void supprimer(Long id){
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medecin introuvable"));
        medecinRepository.delete(medecin);
    }


    @Cacheable(value = "medecins",key = "'patient-' + #id")
    public List<MedecinResponseDto> recupererLesMedcinsParPatient(Long id){
        return medecinMapper.toLsitDto(medecinRepository.recupererLesmedcindunpatient(id));
    }


    @Cacheable(value = "medecins-pages", key = "'list-' + #page + '-' + #size")
    public Page<MedecinResponseDto> consulterMedecinTriparSpecialite(int page,int size){
        Pageable pageable = PageRequest.of(page,size);

        return medecinRepository.findAllByOrderBySpecialiteAsc(pageable).map(medecinMapper::toDto);

    }



    @Cacheable(value = "medecins-pages", key = "'search-' + #specialite + '-' + #page + '-' + #size")
    public Page<MedecinResponseDto>  chercherMedecinParSpecialite(String specialite,int page,int size){
        Pageable pageable = PageRequest.of(page,size);

        return medecinRepository.findAllBySpecialite(specialite,pageable).map(medecinMapper::toDto);

    }
}
