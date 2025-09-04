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
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> criarLivro(@Valid @RequestBody LivroRequestDTO dto){
        LivroResponseDTO novoLivro = livroService.criarLivro(dto);
        return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarLivros(){
        List<LivroResponseDTO> livros = livroService.listarLivros();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Long id){
        return livroService.buscarPorId(id)
                .map(livro -> new ResponseEntity<>(livro, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizarLivro(@PathVariable Long id,
                                                           @Valid @RequestBody LivroRequestDTO dto){
        try {
            LivroResponseDTO atualizado = livroService.atualizarLivro(id, dto);
            return new ResponseEntity<>(atualizado, HttpStatus.OK);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id){
        try {
            livroService.deletarLivro(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}