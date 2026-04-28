package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.mapper.PatientMapper;
import org.example.healthcare.model.Patient;
import org.example.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientMapper patientMapper;

    @Transactional
    public PatientRespenseDto ajouter(PatientRequestDto requestDto){
        Patient patient = patientMapper.toEntity(requestDto);
        Patient patient_saved = patientRepository.save(patient);
        return patientMapper.toDto(patient_saved);
    }

    public PatientRespenseDto consulter(Long id){
        Patient patient_chercher = patientRepository.findById(id).orElse(null);
        return patientMapper.toDto(patient_chercher);
    }

    public List<PatientRespenseDto> consulterTous(){
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.toListDto(patients);
    }

    @Transactional
    public PatientRespenseDto modifier(Long id , PatientRequestDto requestDto){
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient introuvable");
        }
        Patient patient_a_modifier = patientMapper.toEntity(requestDto);
        patient_a_modifier.setId(id);
        return patientMapper.toDto(patientRepository.save(patient_a_modifier));
    }

    @Transactional
    public void supprimer(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient introuvable");
        }
        patientRepository.deleteById(id);
    }

}
