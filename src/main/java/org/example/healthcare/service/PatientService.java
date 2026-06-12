package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.example.healthcare.dto.patient.PatientRequestDto;
import org.example.healthcare.dto.patient.PatientRespenseDto;
import org.example.healthcare.mapper.PatientMapper;
import org.example.healthcare.model.Patient;
import org.example.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientMapper patientMapper;

    @Transactional
    @CacheEvict(value = {"patients", "patients-pages"}, allEntries = true)
    public PatientRespenseDto ajouter(PatientRequestDto requestDto){
        Patient patient = patientMapper.toEntity(requestDto);
        Patient patient_saved = patientRepository.save(patient);
        return patientMapper.toDto(patient_saved);
    }


    @Cacheable(value = "patients" , key = "'single-' + #id")
    public PatientRespenseDto consulter(Long id){
        Patient patient_chercher = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient introuvable"));
        return patientMapper.toDto(patient_chercher);
    }

    @Cacheable(value = "patients" , key = "'all'")
    public List<PatientRespenseDto> consulterTous(){
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.toListDto(patients);
    }

    @Transactional
    @CacheEvict(value = {"patients", "patients-pages"}, allEntries = true)
    public PatientRespenseDto modifier(Long id , PatientRequestDto requestDto){
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient introuvable");
        }
        Patient patient_a_modifier = patientMapper.toEntity(requestDto);
        patient_a_modifier.setId(id);
        return patientMapper.toDto(patientRepository.save(patient_a_modifier));
    }

    @Transactional
    @CacheEvict(value = {"patients", "patients-pages"}, allEntries = true)
    public void supprimer(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient introuvable");
        }
        patientRepository.deleteById(id);
    }




    @Cacheable(value = "patients-pages", key = "'list-' + #sortField + '-' + #sortDirection + '-' + #page + '-' + #size")
    public Page<PatientRespenseDto> consulterPatientTriparnom(String sortField, String sortDirection, int page, int size){
        Sort sort;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortField).ascending();
        } else {
            sort = Sort.by(sortField).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return patientRepository.findAll(pageable).map(patientMapper::toDto);
    }


    @Cacheable(value = "patients-pages", key = "'search-' + #nom + '-' + #page + '-' + #size")
    public  Page<PatientRespenseDto> chercherPatientParNom(String nom, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return patientRepository.findAllByNom(nom,pageable).map(patientMapper::toDto);
    }


    @Cacheable(value = "patients-pages", key = "'search-' + #nom + '-' + #page + '-' + #size")
    public  Page<PatientRespenseDto> listerPaginer(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return patientRepository.findAll(pageable).map(patientMapper::toDto);
    }

}
