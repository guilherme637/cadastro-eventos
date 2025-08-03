package teste.evento.petrobras.presentation.dto.evento;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class EventoResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataEvento;
    private String local;
}
