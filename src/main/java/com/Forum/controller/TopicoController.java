package com.Forum.controller;
import com.Forum.model.*;
import com.Forum.repository.CursoRepository;
import com.Forum.repository.TopicoRepository;
import com.Forum.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoController(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Topico> criarTopico(@RequestBody @Valid TopicoForm form) {
        if (topicoRepository.findByTituloAndMensagem(form.getTitulo(), form.getMensagem()).isPresent()) {
            return ResponseEntity.badRequest().build(); // Já existe um tópico com mesmo título e mensagem
        }

        Usuario usuario = usuarioRepository.findById(form.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Curso curso = cursoRepository.findById(form.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Topico topico = new Topico(null, form.getTitulo(), form.getMensagem(), null, usuario, curso);
        topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }


    @GetMapping
    public Page<Topico> listarTopicos(@PageableDefault(size = 10, sort = "dataCriacao") Pageable paginacao) {
        return topicoRepository.findAll(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> buscarPorId(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topico> atualizarTopico(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();

            // Verificar se já existe um tópico com o mesmo título e mensagem (excluindo o próprio tópico)
            if (topicoRepository.findByTituloAndMensagemAndIdNot(form.getTitulo(), form.getMensagem(), id).isPresent()) {
                return ResponseEntity.badRequest().build();
            }

            topico.setTitulo(form.getTitulo());
            topico.setMensagem(form.getMensagem());

            // Atualizar usuário e curso se os IDs forem fornecidos no formulário
            if (form.getUsuarioId() != null) {
                Usuario usuario = usuarioRepository.findById(form.getUsuarioId())
                        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
                topico.setUsuario(usuario);
            }

            if (form.getCursoId() != null) {
                Curso curso = cursoRepository.findById(form.getCursoId())
                        .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
                topico.setCurso(curso);
            }

            return ResponseEntity.ok(topico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluirTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}