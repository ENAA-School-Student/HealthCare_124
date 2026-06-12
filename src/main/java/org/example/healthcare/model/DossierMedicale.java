package org.example.healthcare.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dossiers_medicaux")
public class DossierMedicale implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message="la date de creation est obligatoire")
    private LocalDateTime dateCreation;


    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private List<Consultation> consultations;

    @OneToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;
}
