package org.example.healthcare.dto.dossierMedicale;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String diagnostic;

    private String observation;

    @NotBlank(message="la date de creation est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateCreation;

    @NotNull(message = "le patient id est nécessaire")
    private Long patientId;

}
