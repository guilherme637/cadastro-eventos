package teste.evento.petrobras.domain.converter.evento;

import teste.evento.petrobras.domain.entity.Evento;
import teste.evento.petrobras.presentation.dto.evento.EventoPutDTO;

import java.util.Optional;

public class EventoConverter {
    public static Evento convertFromPut(Evento evento, EventoPutDTO dto) {
        Optional.ofNullable(dto.getTitulo()).ifPresent(evento::setTitulo);
        Optional.ofNullable(dto.getDescricao()).ifPresent(evento::setDescricao);
        Optional.ofNullable(dto.getDataEvento()).ifPresent(evento::setDataEvento);
        Optional.ofNullable(dto.getLocal()).ifPresent(evento::setLocal);

        return evento;
    }
}
