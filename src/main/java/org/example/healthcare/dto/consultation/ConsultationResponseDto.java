package org.example.healthcare.dto.consultation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationResponseDto {
    private Long id;
    private String diagnostic;
    private String observation;
    private LocalDateTime date_consultation;
    private Long dossierId;
    private Long medecinId;
    private String medecinNom;
    private String medecinPrenom;
}
