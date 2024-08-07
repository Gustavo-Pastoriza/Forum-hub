package com.Forum.repository;
import com.Forum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);
    Optional<Topico> findByTituloAndMensagemAndIdNot(String titulo, String mensagem, Long id);
}