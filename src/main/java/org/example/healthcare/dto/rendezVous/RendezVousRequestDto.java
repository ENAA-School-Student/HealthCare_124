package org.example.healthcare.dto.rendezVous;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.enums.RendezVousStatut;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousRequestDto {

    @NotNull(message = "la date de le rendez-vous est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateRendezVous;

    @NotNull(message = "Le statut du rendez-vous est obligatoire")
    private RendezVousStatut statut;

    @NotNull(message = "L'id patient est nécessaire")
    private Long patientId;

    @NotNull(message = "L'id medecin est nécessaire")
    private Long medecinId;
}
