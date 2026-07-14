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
    public DossierMedicaleResponseDto ajouter(DossierMedicaleRequestDto requestDto) {
        Patient patient = patientRepository.findById(requestDto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient introuvable"));
        if (dossierMedicaleRepository.existsByPatient_Id(requestDto.getPatientId())) {
            throw new IllegalStateException("Ce patient possède déjà un dossier médical.");
        }
        DossierMedicale dossierMedicale = dossierMedicaleMapper.toEntity(requestDto);
        dossierMedicale.setPatient(patient);
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.save(dossierMedicale));
    }

    public DossierMedicaleResponseDto consulter(Long id) {
        DossierMedicale dossier = dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dossier introuvable avec l'id: " + id));
        return dossierMedicaleMapper.toDto(dossier);
    }

    public List<DossierMedicaleResponseDto> consulterTous() {
        return dossierMedicaleMapper.toListDto(dossierMedicaleRepository.findAll());
    }

    @Transactional
    public DossierMedicaleResponseDto modifier(Long id, DossierMedicaleRequestDto requestDto) {
        if (!dossierMedicaleRepository.existsById(id)) {
            throw new EntityNotFoundException("Dossier introuvable avec l'id: " + id);
        }
        Patient patient = patientRepository.findById(requestDto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient introuvable"));
        DossierMedicale dossierMedicale = dossierMedicaleMapper.toEntity(requestDto);
        dossierMedicale.setId(id);
        dossierMedicale.setPatient(patient);
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.save(dossierMedicale));
    }

    @Transactional
    public void supprimer(Long id) {
        DossierMedicale dossier = dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dossier introuvable avec l'id: " + id));
        dossierMedicaleRepository.delete(dossier);
    }

    public DossierMedicaleResponseDto consulterParPatient(Long id) {
        DossierMedicale dossier = dossierMedicaleRepository.findDossierMedicaleByPatient_Id(id);
        if (dossier == null) {
            throw new EntityNotFoundException("Aucun dossier trouvé pour le patient avec l'id: " + id);
        }
        return dossierMedicaleMapper.toDto(dossier);
    }

    public List<ConsultationResponseDto> consulterDossierConsultations(Long id) {
        return consultationMapper.toListDto(consultationRepository.findAllByDossierId(id));
    }
}
