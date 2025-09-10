package io.github.Erissonteixeira.api_crudbiblioteca.controller;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<AutorResponseDTO> criarAutor(@Valid @RequestBody AutorRequestDTO dto){
        AutorResponseDTO response = autorService.criarAutor(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarAutores(){
        List<AutorResponseDTO> autores = autorService.listarAutores();
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscarPorId(@PathVariable Long id){
        return autorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> atualizarAutor(@PathVariable Long id,
                                                           @Valid @RequestBody AutorRequestDTO dto){
        AutorResponseDTO response = autorService.atualizarAutor(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id){
        autorService.deletarAutor(id);
        return ResponseEntity.noContent().build();
    }
}