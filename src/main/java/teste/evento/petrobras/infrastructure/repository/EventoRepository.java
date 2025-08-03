package teste.evento.petrobras.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teste.evento.petrobras.domain.entity.Evento;

import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    Page<Evento> findByDeletadoFalse(Pageable pageable);
    Optional<Evento> findByIdAndDeletadoFalse(Long id);
}
