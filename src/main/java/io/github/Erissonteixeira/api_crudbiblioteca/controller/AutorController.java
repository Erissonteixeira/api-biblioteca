package io.github.Erissonteixeira.api_crudbiblioteca.controller;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<AutorResponseDTO> criarAutor(@Valid @RequestBody AutorRequestDTO dto){
        AutorResponseDTO novoAutor = autorService.criarAutor(dto);
        return new ResponseEntity<>(novoAutor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarAutores(){
        List<AutorResponseDTO> autores = autorService.listarAutores();
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscarPorId(@PathVariable Long id){
        return autorService.buscarPorId(id)
                .map(autor -> new ResponseEntity<>(autor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> atualizarAutor(@PathVariable Long id,
                                                           @Valid @RequestBody AutorRequestDTO dto){
        try {
            AutorResponseDTO atualizado = autorService.atualizarAutor(id, dto);
            return new ResponseEntity<>(atualizado, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id){
        try {
            autorService.deletarAutor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}