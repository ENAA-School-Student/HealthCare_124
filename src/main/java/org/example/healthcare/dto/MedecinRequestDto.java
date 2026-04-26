package org.example.healthcare.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedecinRequestDto {

    @NotBlank(message = "le nom est obligatoire")
    private String nom;

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

    @NotBlank(message = "la specialite est obligatoire")
    private String specialite;

}
