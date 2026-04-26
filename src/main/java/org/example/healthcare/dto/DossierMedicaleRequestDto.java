package org.example.healthcare.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DossierMedicaleRequestDto {

    private String diagnostic;

    private String observation;

    @NotBlank(message="la date de creation est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateCreation;

    @NotNull(message = "le patient id est nécessaire")
    private Long patientId;

}
