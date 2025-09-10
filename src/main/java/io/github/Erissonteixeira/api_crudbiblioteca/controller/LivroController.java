package io.github.Erissonteixeira.api_crudbiblioteca.controller;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> criarLivro(@Valid @RequestBody LivroRequestDTO dto){
        LivroResponseDTO response = livroService.criarLivro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarLivros(){
        List<LivroResponseDTO> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Long id){
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizarLivro(@PathVariable Long id,
                                                           @Valid @RequestBody LivroRequestDTO dto){
       LivroResponseDTO response = livroService.atualizarLivro(id, dto);
       return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id){
       livroService.deletarLivro(id);
       return ResponseEntity.noContent().build();
    }
}