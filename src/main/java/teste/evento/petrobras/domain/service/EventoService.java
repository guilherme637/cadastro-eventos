package teste.evento.petrobras.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import teste.evento.petrobras.domain.build.evento.EventoBuilder;
import teste.evento.petrobras.domain.entity.Evento;
import teste.evento.petrobras.infrastructure.mapper.EventoMapper;
import teste.evento.petrobras.infrastructure.repository.EventoRepository;
import teste.evento.petrobras.presentation.dto.evento.*;

import java.util.Optional;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;

    public EventoService(EventoRepository eventoRepository, EventoMapper eventoMapper) {
        this.eventoRepository = eventoRepository;
        this.eventoMapper = eventoMapper;
    }

    public PaginaResponseDTO<EventoResponseDTO> getListEventos(EventoFiltroDTO dto) {
        Page<Evento> eventos = this.eventoRepository.findByDeletadoFalse(PageRequest.of(dto.getPagina(), dto.getTamanho()));

        Page<EventoResponseDTO> responseEventos = eventos.map(eventoMapper::toDto);

        return PaginaResponseDTO.<EventoResponseDTO>builder()
                .itens(responseEventos.getContent())
                .pagina(responseEventos.getNumber())
                .tamanho(responseEventos.getSize())
                .total(responseEventos.getTotalElements())
                .ultima(responseEventos.isLast())
                .build();
    }

    public EventoResponseDTO getEvento(EventoGetDTO dto) {
        return eventoMapper.toDto(
            this.buscarEvento(dto.getId())
        );
    }

    public void criar(EventoPostDTO dto) {
        Evento evento = EventoBuilder.buildFromPostDto(dto);

        this.eventoRepository.save(evento);
    }

    public void atualizar(Long id, EventoPutDTO dto) {
        Evento evento = this.buscarEvento(id);

        Optional.ofNullable(dto.getTitulo()).ifPresent(evento::setTitulo);
        Optional.ofNullable(dto.getDescricao()).ifPresent(evento::setDescricao);
        Optional.ofNullable(dto.getDataEvento()).ifPresent(evento::setDataEvento);
        Optional.ofNullable(dto.getLocal()).ifPresent(evento::setLocal);


        this.eventoRepository.save(evento);
    }

    public void deletar(Long id) {
        Evento evento = this.buscarEvento(id);

        evento.setDeletado(true);

        this.eventoRepository.save(evento);
    }

    private Evento buscarEvento(Long id) {
        return this.eventoRepository.findByIdAndDeletadoFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }
}
