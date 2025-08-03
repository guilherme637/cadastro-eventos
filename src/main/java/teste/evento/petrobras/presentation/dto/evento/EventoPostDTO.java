package teste.evento.petrobras.presentation.dto.evento;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventoPostDTO {

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 100, message = "O título deve conter no máximo 100 caracteres.")
    private String titulo;

    @Size(max = 1000, message = "A descrição deve conter no máximo 1000 caracteres.")
    private String descricao;

    @NotNull(message = "A data e hora do evento são obrigatórias.")
    @FutureOrPresent(message = "A data do evento deve ser no presente ou futuro.")
    private LocalDateTime dataEvento;

    @Size(max = 200, message = "O local deve ter no máximo 200 caracteres.")
    private String local;
}
