package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.healthcare.dto.consultation.ConsultationRequestDto;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.mapper.ConsultationMapper;
import org.example.healthcare.model.Consultation;
import org.example.healthcare.model.DossierMedicale;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.repository.ConsultationRepository;
import org.example.healthcare.repository.DossierMedicaleRepository;
import org.example.healthcare.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ConsultationResponseDto ajouter(ConsultationRequestDto requestDto) {
        DossierMedicale dossier = dossierMedicaleRepository.findById(requestDto.getDossier_medicale_id())
                .orElseThrow(() -> new EntityNotFoundException("Dossier introuvable"));
        Medecin medecin = medecinRepository.findById(requestDto.getMedecin_id())
                .orElseThrow(() -> new EntityNotFoundException("Medecin introuvable"));

        Consultation consultation = consultationMapper.toEntity(requestDto);
        consultation.setDossier(dossier);
        consultation.setMedecin(medecin);
        return consultationMapper.toDto(consultationRepository.save(consultation));
    }

    public ConsultationResponseDto consulter(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation introuvable avec l'id: " + id));
        return consultationMapper.toDto(consultation);
    }

    public List<ConsultationResponseDto> consulterTous() {
        return consultationMapper.toListDto(consultationRepository.findAll());
    }

    @Transactional
    public ConsultationResponseDto modifier(Long id, ConsultationRequestDto requestDto) {
        if (!consultationRepository.existsById(id)) {
            throw new EntityNotFoundException("Consultation introuvable avec l'id: " + id);
        }
        DossierMedicale dossier = dossierMedicaleRepository.findById(requestDto.getDossier_medicale_id())
                .orElseThrow(() -> new EntityNotFoundException("Dossier introuvable"));
        Medecin medecin = medecinRepository.findById(requestDto.getMedecin_id())
                .orElseThrow(() -> new EntityNotFoundException("Medecin introuvable"));

        Consultation consultation = consultationMapper.toEntity(requestDto);
        consultation.setId(id);
        consultation.setDossier(dossier);
        consultation.setMedecin(medecin);
        return consultationMapper.toDto(consultationRepository.save(consultation));
    }

    @Transactional
    public void supprimer(Long id) {
        if (!consultationRepository.existsById(id)) {
            throw new EntityNotFoundException("Consultation introuvable avec l'id: " + id);
        }
        consultationRepository.deleteById(id);
    }


    public Page<ConsultationResponseDto> paginateConsultations(int page , int size){
        Pageable pageable = PageRequest.of(page, size);
        return consultationRepository.findAll(pageable).map(consultationMapper::toDto);
    }










}
