package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.healthcare.dto.consultation.ConsultationRequestDto;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.mapper.ConsultationMapper;
import org.example.healthcare.model.Consultation;
import org.example.healthcare.repository.ConsultationRepository;
import org.example.healthcare.repository.DossierMedicaleRepository;
import org.example.healthcare.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private DossierMedicaleRepository dossierMedicaleRepository;


    @Transactional
    public ConsultationResponseDto ajouter(ConsultationRequestDto requestDto){
        if (!dossierMedicaleRepository.existsById(requestDto.getDossier_medicale_id())){
            throw new EntityNotFoundException("Dossier introuvable");
        }
        if (!medecinRepository.existsById(requestDto.getMedecin_id())){
            throw new EntityNotFoundException("Medecin introuvable");
        }
        Consultation consultation = consultationMapper.toEntity(requestDto);
        consultation.setDossier(dossierMedicaleRepository.findById(requestDto.getDossier_medicale_id()).get());
        consultation.setMedecin(medecinRepository.findById(requestDto.getMedecin_id()).get());
        return consultationMapper.toDto(consultationRepository.save(consultation));
    }


    public ConsultationResponseDto consulter(Long id){
        return consultationMapper.toDto(consultationRepository.findById(id).get());
    }

    public List<ConsultationResponseDto> consulterTous(){
        return consultationMapper.toListDto(consultationRepository.findAll());
    }

    public ConsultationResponseDto modifier(Long id,ConsultationRequestDto requestDto){
        if (!consultationRepository.existsById(id)){
            throw new EntityNotFoundException("consultations introuvable");
        }
        Consultation consultation = consultationMapper.toEntity(requestDto);
        consultation.setDossier(dossierMedicaleRepository.findById(requestDto.getDossier_medicale_id()).get());
        consultation.setMedecin(medecinRepository.findById(requestDto.getMedecin_id()).get());
        return consultationMapper.toDto(consultationRepository.save(consultation));
    }


    @Transactional
    public void supprimer(Long id){
        if (!consultationRepository.existsById(id)){
            throw new EntityNotFoundException("consultations introuvable");
        }
        consultationRepository.deleteById(id);
    }
}
