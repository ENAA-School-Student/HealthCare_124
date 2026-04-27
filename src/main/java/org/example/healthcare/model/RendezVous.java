package org.example.healthcare.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.enums.RendezVousStatut;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rendez_vous")
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "la date de le rendez-vous est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateRendezVous;

    @NotNull(message = "Le statut du rendez-vous est obligatoire")
    @Enumerated(EnumType.STRING)
    private RendezVousStatut statut;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;




}
