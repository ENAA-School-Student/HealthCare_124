package org.example.healthcare.service;


import org.example.healthcare.mapper.MedecinMapper;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedecinService {

    @Autowired
    MedecinRepository repository;

    @Autowired
    MedecinMapper medecinMapper;
}
