package teste.evento.petrobras.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.evento.petrobras.domain.security.XssValidator;
import teste.evento.petrobras.domain.service.EventoService;
import teste.evento.petrobras.presentation.dto.evento.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost")
public class EventoController {
    private EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/events")
    @Operation(summary = "Lista todos eventos que não estão deletados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginaResponseDTO.class)))
    })
    public ResponseEntity<PaginaResponseDTO<EventoResponseDTO>> getList(@ModelAttribute EventoFiltroDTO dto) {
        PaginaResponseDTO<EventoResponseDTO> eventos = this.eventoService.getListEventos(dto);

        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/events/{id}")
    @Operation(summary = "Busca evento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<EventoResponseDTO> getList(@PathVariable Long id) {
        EventoGetDTO dto = EventoGetDTO.builder().id(id).build();

        EventoResponseDTO eventos = this.eventoService.getEvento(dto);

        return ResponseEntity.ok(eventos);
    }

    @PostMapping("/events")
    @Operation(summary = "Cria um novo evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> post(@Valid @RequestBody EventoPostDTO dto) {
        XssValidator.validar(dto);

        this.eventoService.criar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/events/{id}")
    @Operation(summary = "Atualiza um evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> put(@PathVariable Long id, @Valid @RequestBody EventoPutDTO dto) {
        XssValidator.validar(dto);

        this.eventoService.atualizar(id, dto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/events/{id}")
    @Operation(summary = "Deleta um evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> put(@PathVariable Long id) {
        this.eventoService.deletar(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
