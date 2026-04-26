package org.example.healthcare.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultations")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String observation;
    private LocalDateTime date_consultation;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private DossierMedicale dossier;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

}
