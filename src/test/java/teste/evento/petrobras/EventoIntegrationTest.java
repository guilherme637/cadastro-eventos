package teste.evento.petrobras;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import teste.evento.petrobras.domain.service.EventoService;
import teste.evento.petrobras.presentation.dto.evento.EventoResponseDTO;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = teste.evento.petrobras.controller.EventoController.class)
public class EventoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService eventoService; // mock do service para evitar carregar banco

    @Test
    public void deveRetornarEventoPeloId() throws Exception {
        EventoResponseDTO dto = EventoResponseDTO.builder()
                .id(1L)
                .titulo("Evento teste")
                .build();

        when(eventoService.getEvento(any())).thenReturn(dto);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Evento teste"));
    }
}
