package org.example.healthcare.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consultations")
public class Consultation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @NotNull(message = "la date de consultation est obligatoire")
    private LocalDateTime date_consultation;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    @JsonIgnore
    private DossierMedicale dossier;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    @JsonIgnore
    private Medecin medecin;

}
