package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.enums.RendezVousStatut;
import org.example.healthcare.mapper.RendezVousMapper;
import org.example.healthcare.model.RendezVous;
import org.example.healthcare.repository.MedecinRepository;
import org.example.healthcare.repository.PatientRepository;
import org.example.healthcare.repository.RendezVousRepository;
import org.example.healthcare.utils.ExelExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private RendezVousMapper rendezVousMapper;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PatientRepository patientRepository;


    @Transactional
    @CacheEvict(value = {"rendezVous", "rendezVous-pages"}, allEntries = true)
    public RendezVousResponseDto ajouter(RendezVousRequestDto requestDto){
        if (!patientRepository.existsById(requestDto.getPatientId())) {
            throw new EntityNotFoundException("Patient introuvable");
        }
        if (!medecinRepository.existsById(requestDto.getMedecinId())){
            throw new EntityNotFoundException("Medecin introuvable");
        }
        if (rendezVousRepository.existsByMedecinIdAndDateRendezVous(requestDto.getMedecinId(),requestDto.getDateRendezVous())){
            throw new IllegalStateException("Le médecin est déjà pris à cette date et heure.");
        }
        RendezVous rendezVous = rendezVousMapper.toEntity(requestDto);
        rendezVous.setPatient(patientRepository.findById(requestDto.getPatientId()).get());
        rendezVous.setMedecin(medecinRepository.findById(requestDto.getMedecinId()).get());
        return rendezVousMapper.toDto(rendezVousRepository.save(rendezVous));
    }



    @Cacheable(value = "rendezVous" , key = "'entity-' + #id")
    public RendezVous findById(Long id) {
        return rendezVousRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable"));
    }

    @Cacheable(value = "rendezVous" , key = "'single-' + #id")
    public RendezVousResponseDto consulter(Long id){
        return rendezVousMapper.toDto(rendezVousRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable")));
    }


    @Cacheable(value = "rendezVous" , key = "'list-all'")
    public List<RendezVousResponseDto> consulterTous(){
        return rendezVousMapper.toListDto(rendezVousRepository.findAll());
    }

    @CacheEvict(value = {"rendezVous", "rendezVous-pages"}, allEntries = true)
    public RendezVousResponseDto modifier(Long id , RendezVousRequestDto requestDto){
        if (!rendezVousRepository.existsById(id)){
            throw new EntityNotFoundException("Rendez-vous introuvable");
        }
        RendezVous rendezVous_a_changer = rendezVousMapper.toEntity(requestDto);
        rendezVous_a_changer.setId(id);
        rendezVous_a_changer.setPatient(patientRepository.findById(requestDto.getPatientId()).get());
        rendezVous_a_changer.setMedecin(medecinRepository.findById(requestDto.getMedecinId()).get());
        return rendezVousMapper.toDto(rendezVousRepository.save(rendezVous_a_changer));
    }

    @CacheEvict(value = {"rendezVous", "rendezVous-pages"}, allEntries = true)
    public void modifierRendezVousStatut(Long id,RendezVousStatut rendezVousStatut){
        if (!rendezVousRepository.existsById(id)){
            throw new EntityNotFoundException("Rendez-vous introuvable");
        }
        RendezVous rendezVous = rendezVousRepository.findById(id).get();
        rendezVous.setStatut(rendezVousStatut);
        rendezVousRepository.save(rendezVous);
    }

    @CacheEvict(value = {"rendezVous", "rendezVous-pages"}, allEntries = true)
    public void supprimer(Long id){
        if (!rendezVousRepository.existsById(id)){
            throw new EntityNotFoundException("Rendez-vous introuvable");
        }
        rendezVousRepository.deleteById(id);
    }


    @Cacheable(value = "rendezVous" , key = "'patient-' + #id")
    public List<RendezVousResponseDto> chercherRebdezVousParPatient(Long id){
        return rendezVousMapper.toListDto(rendezVousRepository.findRendezVousByPatientId(id));
    }

    @Cacheable(value = "rendezVous" , key = "'medecin-' + #id")
    public List<RendezVousResponseDto> chercherRebdezVousParMedecin(Long id){
        return rendezVousMapper.toListDto(rendezVousRepository.findRendezVousByMedecinId(id));
    }


    @Cacheable(value = "rendezVous-pages", key = "'list-' + #page + '-' + #size")
    public Page<RendezVousResponseDto> consulterRenderVousTriPAreDate(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        return rendezVousRepository.findAllByOrderByDateRendezVousAsc(pageable).map(rendezVousMapper::toDto);
    }



    @Cacheable(value = "rendezVous-pages", key = "'status-' + #statut + '-' + #page + '-' + #size")
    public Page<RendezVousResponseDto> consulterRendervousparStatut(RendezVousStatut statut,int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        return rendezVousRepository.findAllByStatut(statut,pageable).map(rendezVousMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ByteArrayInputStream exportRendezVousParPatient(Long patientId) {
        List<RendezVous> rendezVous = rendezVousRepository.findRendezVousByPatientId(patientId);
        List<RendezVousResponseDto> dtos = rendezVousMapper.toListDto(rendezVous);
        return ExelExport.listRendezVousToExcel(dtos);
    }



}
