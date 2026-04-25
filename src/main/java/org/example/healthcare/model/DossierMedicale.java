package org.example.healthcare.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.micrometer.observation.Observation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dossiers_medicales")
public class DossierMedicale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String diagnostic;

    private String observation;

    @NotBlank(message="la date de creation est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateCreation;

    @OneToOne
    private Patient patient;
}
