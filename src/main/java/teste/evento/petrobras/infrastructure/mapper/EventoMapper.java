package teste.evento.petrobras.infrastructure.mapper;

import org.mapstruct.Mapper;
import teste.evento.petrobras.domain.entity.Evento;
import teste.evento.petrobras.presentation.dto.evento.EventoResponseDTO;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    EventoResponseDTO toDto(Evento evento);
}
