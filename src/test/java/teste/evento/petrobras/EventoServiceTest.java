package teste.evento.petrobras;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;
import teste.evento.petrobras.domain.entity.Evento;
import teste.evento.petrobras.domain.service.EventoService;
import teste.evento.petrobras.infrastructure.mapper.EventoMapper;
import teste.evento.petrobras.infrastructure.repository.EventoRepository;
import teste.evento.petrobras.presentation.dto.evento.EventoGetDTO;
import teste.evento.petrobras.presentation.dto.evento.EventoPostDTO;
import teste.evento.petrobras.presentation.dto.evento.EventoResponseDTO;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventoServiceTest {

    private EventoRepository eventoRepository;
    private EventoMapper eventoMapper;
    private EventoService eventoService;

    @BeforeEach
    public void setUp() {
        eventoRepository = mock(EventoRepository.class);
        eventoMapper = mock(EventoMapper.class);
        eventoService = new EventoService(eventoRepository, eventoMapper);
    }

    @Test
    public void deveriaRetornarEventoQuandoEncontrar() {
        Long id = 1L;
        Evento evento = new Evento();
        evento.setId(id);
        evento.setTitulo("Teste");
        evento.setDescricao("Descrição");
        evento.setDataEvento(LocalDateTime.now());
        evento.setLocal("Local");

        EventoResponseDTO dto = EventoResponseDTO.builder()
                .id(id)
                .titulo("Teste")
                .descricao("Descrição")
                .dataEvento(evento.getDataEvento())
                .local("Local")
                .build();

        when(eventoRepository.findByIdAndDeletadoFalse(id)).thenReturn(Optional.of(evento));
        when(eventoMapper.toDto(evento)).thenReturn(dto);

        EventoResponseDTO resultado = eventoService.getEvento(EventoGetDTO.builder().id(id).build());

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Teste", resultado.getTitulo());
        verify(eventoRepository).findByIdAndDeletadoFalse(id);
        verify(eventoMapper).toDto(evento);
    }

    @Test
    public void deveriaLancarExcecaoQuandoNaoEncontrarEvento() {
        Long id = 123L;
        when(eventoRepository.findByIdAndDeletadoFalse(id)).thenReturn(Optional.empty());

        ResponseStatusException excecao = assertThrows(ResponseStatusException.class, () -> {
            eventoService.getEvento(EventoGetDTO.builder().id(id).build());
        });

        assertEquals("Evento não encontrado", excecao.getReason());
        assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, excecao.getStatusCode());
        verify(eventoRepository).findByIdAndDeletadoFalse(id);
        verifyNoInteractions(eventoMapper);
    }

    @Test
    public void deveriaCriarEventoChamandoBuilderESalvando() {
        EventoPostDTO dto = EventoPostDTO.builder().build();
        Evento evento = new Evento();

        try (MockedStatic<teste.evento.petrobras.domain.build.evento.EventoBuilder> builderMock = mockStatic(teste.evento.petrobras.domain.build.evento.EventoBuilder.class)) {
            builderMock.when(() -> teste.evento.petrobras.domain.build.evento.EventoBuilder.buildFromPostDto(dto)).thenReturn(evento);
            when(eventoRepository.save(evento)).thenReturn(evento);

            eventoService.criar(dto);

            builderMock.verify(() -> teste.evento.petrobras.domain.build.evento.EventoBuilder.buildFromPostDto(dto));
            verify(eventoRepository).save(evento);
        }
    }
}