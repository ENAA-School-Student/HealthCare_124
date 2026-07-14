package org.example.healthcare.dto.auth;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.enums.UserRoles;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResquestDTO {
    @NotBlank(message = "User name est obligatoire")
    private String userName;

    @NotBlank(message = "l'email est obligatoire")
    @Email(message = "l'email format invalide")
    private String email;


    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;


    @NotNull(message = "user role est obligatoire")
    @Enumerated(EnumType.STRING)
    private UserRoles role;



    private MedecinDTO medecinDTO;
    private PatientDTO patientDTO;

    public String getName(){
        return userName;
    }
}
