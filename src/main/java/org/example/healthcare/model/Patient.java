package org.example.healthcare.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.enums.UserRoles;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patient extends User implements Serializable {

    @NotBlank(message = "le nom est obligatoire")
    private String nom;

    @NotBlank(message = "le prénom est obligatoire")
    private String prenom;


    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(
            regexp = "^\\+?[0-9]{8,15}$",
            message = "Numéro de téléphone invalide"
    )
    private String telephone;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;


    @OneToOne(mappedBy = "patient")
    private DossierMedicale dossierMedicale;

    @OneToMany(mappedBy = "patient")
    private List<RendezVous> rendezVous;



    public Patient() {
        super();
        this.setRole(UserRoles.PATIENT);
    }


}
