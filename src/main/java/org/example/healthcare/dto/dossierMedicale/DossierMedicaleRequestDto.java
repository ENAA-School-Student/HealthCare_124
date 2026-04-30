package org.example.healthcare.dto.dossierMedicale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierMedicaleRequestDto {

    @NotNull(message="la date de creation est obligatoire")
    private LocalDateTime dateCreation;

    @NotNull(message = "le patient id est nécessaire")
    private Long patientId;

}
