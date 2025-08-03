package teste.evento.petrobras.presentation.dto.evento;

import lombok.Data;

@Data
public class EventoFiltroDTO {
    private int pagina = 0;
    private int tamanho = 10;
}