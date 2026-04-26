package org.example.healthcare.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto {
    @NotBlank(message = "le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "l'email est obligatoire")
    @Email(message = "Invalid email format")
    private String email;


    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(
            regexp = "^\\+?[0-9]{8,15}$",
            message = "Numéro de téléphone invalide"
    )
    private String telephone;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

}
