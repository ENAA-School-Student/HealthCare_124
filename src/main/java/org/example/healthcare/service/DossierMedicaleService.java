package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleRequestDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleResponseDto;
import org.example.healthcare.mapper.ConsultationMapper;
import org.example.healthcare.mapper.DossierMedicaleMapper;
import org.example.healthcare.model.DossierMedicale;
import org.example.healthcare.model.Patient;
import org.example.healthcare.repository.ConsultationRepository;
import org.example.healthcare.repository.DossierMedicaleRepository;
import org.example.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DossierMedicaleService {

    @Autowired
    private DossierMedicaleRepository dossierMedicaleRepository;

    @Autowired
    private DossierMedicaleMapper dossierMedicaleMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private ConsultationMapper consultationMapper;


    @Transactional
    public DossierMedicaleResponseDto ajouter(DossierMedicaleRequestDto requestDto){
        if (!patientRepository.existsById(requestDto.getPatientId())) {
            throw new EntityNotFoundException("Patient introuvable");
        }
        if (!dossierMedicaleRepository.existsByPatient_Id(requestDto.getPatientId())){
            throw new IllegalStateException("Ce patient possède déjà un dossier médical.");
        }
        DossierMedicale dossierMedicale = dossierMedicaleMapper.toEnity(requestDto);
        dossierMedicale.setPatient(patientRepository.findById(requestDto.getPatientId()).get());
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.save(dossierMedicale));
    }

    public DossierMedicaleResponseDto consulter(Long id){
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.findById(id).orElse(null));
    }

    public List<DossierMedicaleResponseDto> consulterTous(){
        return dossierMedicaleMapper.toListDto(dossierMedicaleRepository.findAll());
    }


    @Transactional
    public DossierMedicaleResponseDto modifier(Long id, DossierMedicaleRequestDto requestDto){
        if (!dossierMedicaleRepository.existsById(id)){
            throw new EntityNotFoundException("Dossier introuvable");
        }
        if (!patientRepository.existsById(requestDto.getPatientId())) {
            throw new EntityNotFoundException("Patient introuvable");
        }
        DossierMedicale dossierMedicale = dossierMedicaleMapper.toEnity(requestDto);
        dossierMedicale.setId(id);
        dossierMedicale.setPatient(patientRepository.findById(requestDto.getPatientId()).get());
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.save(dossierMedicale));
    }

    public void supprimer(Long id){
        if (!dossierMedicaleRepository.existsById(id)){
            throw new EntityNotFoundException("Dossier introuvable");
        }
        dossierMedicaleRepository.deleteById(id);
    }

    public DossierMedicaleResponseDto consulterParPatient(Long id){
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.findDossierMedicaleByPatient_Id(id));
    }

    public List<ConsultationResponseDto> consulterDossierConsultations(Long id){
        return consultationMapper.toListDto(consultationRepository.findAllByDossierId(id));
    }



}
