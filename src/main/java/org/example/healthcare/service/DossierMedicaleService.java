package org.example.healthcare.service;


import jakarta.persistence.EntityNotFoundException;
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
import org.example.healthcare.utils.PdfExport;
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
    @CacheEvict(value = {"dossierMedicale", "dossierMedicale-pages"}, allEntries = true)
    public DossierMedicaleResponseDto ajouter(DossierMedicaleRequestDto requestDto){
        if (!patientRepository.existsById(requestDto.getPatientId())) {
            throw new EntityNotFoundException("Patient introuvable");
        }
        if (dossierMedicaleRepository.existsByPatient_Id(requestDto.getPatientId())){
            throw new IllegalStateException("Ce patient possède déjà un dossier médical.");
        }
        DossierMedicale dossierMedicale = dossierMedicaleMapper.toEnity(requestDto);
        dossierMedicale.setPatient(patientRepository.findById(requestDto.getPatientId()).get());
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.save(dossierMedicale));
    }

    @Cacheable(value = "dossierMedicale",key = "'entity-' + #id")
    public DossierMedicale findById(Long id) {
        return dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dossier introuvable"));
    }

    @Cacheable(value = "dossierMedicale",key = "'single-' + #id")
    public DossierMedicaleResponseDto consulter(Long id){
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dossier introuvable")));
    }

    @Cacheable(value ="dossierMedicale" , key = "'list-all'")
    public List<DossierMedicaleResponseDto> consulterTous(){
        return dossierMedicaleMapper.toListDto(dossierMedicaleRepository.findAll());
    }


    @Transactional
    @CacheEvict(value = {"dossierMedicale", "dossierMedicale-pages"}, allEntries = true)
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

    @Transactional
    @CacheEvict(value = {"dossierMedicale", "dossierMedicale-pages"}, allEntries = true)
    public void supprimer(Long id){
        DossierMedicale dossier = dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dossier introuvable"));
        dossierMedicaleRepository.delete(dossier);
    }


    @Cacheable(value = "dossierMedicale",key = "'patient-' + #id")
    public DossierMedicaleResponseDto consulterParPatient(Long id){
        return dossierMedicaleMapper.toDto(dossierMedicaleRepository.findDossierMedicaleByPatient_Id(id));
    }


    @Cacheable(value ="consultations" , key = "'dossier-' + #id")
    public List<ConsultationResponseDto> consulterDossierConsultations(Long id){
        return consultationMapper.toListDto(consultationRepository.findAllByDossierId(id));
    }


    @Cacheable(value = "dossierMedicale-pages", key = "'list-' + #page + '-' + #size")
    public Page<DossierMedicaleResponseDto> consulterTousPagine(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return dossierMedicaleRepository.findAll(pageable).map(dossierMedicaleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ByteArrayInputStream exportPdf(Long id) {
        DossierMedicale dossier = dossierMedicaleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dossier introuvable"));
        
        DossierMedicaleResponseDto dto = dossierMedicaleMapper.toDto(dossier);
        PdfExport pdfExport = new PdfExport();
        return pdfExport.genererPdf(dto);
    }

    @Transactional(readOnly = true)
    public ByteArrayInputStream exportPdfByPatientId(Long patientId) {
        DossierMedicale dossier = dossierMedicaleRepository.findDossierMedicaleByPatient_Id(patientId);
        if (dossier == null) {
            throw new EntityNotFoundException("Dossier introuvable pour ce patient");
        }
        
        DossierMedicaleResponseDto dto = dossierMedicaleMapper.toDto(dossier);
        PdfExport pdfExport = new PdfExport();
        return pdfExport.genererPdf(dto);
    }

    @Transactional(readOnly = true)
    public ByteArrayInputStream exportSimpleReport(Long patientId) {
        DossierMedicale dossier = dossierMedicaleRepository.findDossierMedicaleByPatient_Id(patientId);
        if (dossier == null) {
            throw new EntityNotFoundException("Dossier introuvable pour ce patient");
        }

        DossierMedicaleResponseDto dto = dossierMedicaleMapper.toDto(dossier);
        PdfExport pdfExport = new PdfExport();
        return pdfExport.genererRapportSimple(dto);
    }



}
