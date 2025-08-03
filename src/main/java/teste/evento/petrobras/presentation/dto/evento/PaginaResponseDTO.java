package teste.evento.petrobras.presentation.dto.evento;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PaginaResponseDTO<T> {
    private List<T> itens;
    private int pagina;
    private int tamanho;
    private long total;
    private boolean ultima;
}