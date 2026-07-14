package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.healthcare.dto.rendezVous.RendezVousRequestDto;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;
import org.example.healthcare.enums.RendezVousStatut;
import org.example.healthcare.mapper.RendezVousMapper;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.model.Patient;
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
    public RendezVousResponseDto ajouter(RendezVousRequestDto requestDto) {
        Patient patient = patientRepository.findById(requestDto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient introuvable"));
        Medecin medecin = medecinRepository.findById(requestDto.getMedecinId())
                .orElseThrow(() -> new EntityNotFoundException("Medecin introuvable"));
        if (rendezVousRepository.existsByMedecinIdAndDateRendezVous(requestDto.getMedecinId(), requestDto.getDateRendezVous())) {
            throw new IllegalStateException("Le médecin est déjà pris à cette date et heure.");
        }
        RendezVous rendezVous = rendezVousMapper.toEntity(requestDto);
        rendezVous.setPatient(patient);
        rendezVous.setMedecin(medecin);
        return rendezVousMapper.toDto(rendezVousRepository.save(rendezVous));
    }

    public RendezVousResponseDto consulter(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable avec l'id: " + id));
        return rendezVousMapper.toDto(rendezVous);
    }

    public List<RendezVousResponseDto> consulterTous() {
        return rendezVousMapper.toListDto(rendezVousRepository.findAll());
    }

    @Transactional
    public RendezVousResponseDto modifier(Long id, RendezVousRequestDto requestDto) {
        if (!rendezVousRepository.existsById(id)) {
            throw new EntityNotFoundException("Rendez-vous introuvable avec l'id: " + id);
        }
        Patient patient = patientRepository.findById(requestDto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient introuvable"));
        Medecin medecin = medecinRepository.findById(requestDto.getMedecinId())
                .orElseThrow(() -> new EntityNotFoundException("Medecin introuvable"));

        RendezVous rendezVous = rendezVousMapper.toEntity(requestDto);
        rendezVous.setId(id);
        rendezVous.setPatient(patient);
        rendezVous.setMedecin(medecin);
        return rendezVousMapper.toDto(rendezVousRepository.save(rendezVous));
    }

    @Transactional
    public void modifierRendezVousStatut(Long id, RendezVousStatut rendezVousStatut) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable avec l'id: " + id));
        rendezVous.setStatut(rendezVousStatut);
        rendezVousRepository.save(rendezVous);
    }

    @Transactional
    public void supprimer(Long id) {
        if (!rendezVousRepository.existsById(id)) {
            throw new EntityNotFoundException("Rendez-vous introuvable avec l'id: " + id);
        }
        rendezVousRepository.deleteById(id);
    }

    public List<RendezVousResponseDto> chercherRebdezVousParPatient(Long id) {
        return rendezVousMapper.toListDto(rendezVousRepository.findRendezVousByPatientId(id));
    }

    public List<RendezVousResponseDto> chercherRebdezVousParMedecin(Long id) {
        return rendezVousMapper.toListDto(rendezVousRepository.findRendezVousByMedecinId(id));
    }
}
