package teste.evento.petrobras.domain.build.evento;

import teste.evento.petrobras.domain.entity.Evento;
import teste.evento.petrobras.presentation.dto.evento.EventoPostDTO;

public class EventoBuilder {
    public static Evento buildFromPostDto(EventoPostDTO dto) {
        Evento evento = new Evento();

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataEvento(dto.getDataEvento());
        evento.setLocal(dto.getLocal());

        return evento;
    }
}
