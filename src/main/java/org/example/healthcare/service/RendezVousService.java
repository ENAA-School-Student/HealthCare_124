package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.enums.RendezVousStatut;
import org.example.healthcare.mapper.RendezVousMapper;
import org.example.healthcare.model.RendezVous;
import org.example.healthcare.repository.MedecinRepository;
import org.example.healthcare.repository.PatientRepository;
import org.example.healthcare.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public RendezVousResponseDto consulter(Long id){
        return rendezVousMapper.toDto(rendezVousRepository.findById(id).orElse(null));
    }

    public List<RendezVousResponseDto> consulterTous(){
        return rendezVousMapper.toListDto(rendezVousRepository.findAll());
    }

    public RendezVousResponseDto modifier(Long id , RendezVousRequestDto requestDto){
        if (!rendezVousRepository.existsById(id)){
            throw new EntityNotFoundException("Rendez-vous introuvable");
        }
        RendezVous rendezVous_a_changer = rendezVousMapper.toEntity(requestDto);
        rendezVous_a_changer.setId(id);
        return rendezVousMapper.toDto(rendezVousRepository.save(rendezVous_a_changer));
    }

    public void modifierRendezVousStatut(Long id,RendezVousStatut rendezVousStatut){
        if (!rendezVousRepository.existsById(id)){
            throw new EntityNotFoundException("Rendez-vous introuvable");
        }
        RendezVous rendezVous = rendezVousRepository.findById(id).get();
        rendezVous.setStatut(rendezVousStatut);
        rendezVousRepository.save(rendezVous);
    }

    public void supprimer(Long id){
        if (!rendezVousRepository.existsById(id)){
            throw new EntityNotFoundException("Rendez-vous introuvable");
        }
        medecinRepository.deleteById(id);
    }


    public List<RendezVousResponseDto> chercherRebdezVousParPatient(Long id){
        return rendezVousMapper.toListDto(rendezVousRepository.findRendezVousByPatientId(id));
    }

    public List<RendezVousResponseDto> chercherRebdezVousParMedecin(Long id){
        return rendezVousMapper.toListDto(rendezVousRepository.findRendezVousByMedecinId(id));
    }




}
