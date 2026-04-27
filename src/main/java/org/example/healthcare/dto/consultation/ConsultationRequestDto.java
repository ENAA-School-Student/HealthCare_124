package org.example.healthcare.dto.consultation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.boot.internal.Abstract;

import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationRequestDto {
    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @NotNull(message = "la date de consultation est obligatoire")
    private LocalDateTime date_consultation;

    @NotNull(message = "le dossier medicale id est nécessaire")
    private Long dossier_medicale_id;

    @NotNull(message = "le medecin id est nécessaire")
    private Long medecin_id;
}
