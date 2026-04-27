package org.example.healthcare.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="medecins")
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank(message = "le nom est obligatoire")
    private String nom;

    @NotBlank(message = "le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "l'email est obligatoire")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "la specialite est obligatoire")
    private String specialite;


    @OneToMany(mappedBy = "medecin")
    private List<RendezVous> rendezVous;

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL)
    private List<Consultation> consultations;


}
