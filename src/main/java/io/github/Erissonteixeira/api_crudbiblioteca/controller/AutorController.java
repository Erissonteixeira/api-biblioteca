package io.github.Erissonteixeira.api_crudbiblioteca.controller;

import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import io.github.Erissonteixeira.api_crudbiblioteca.service.AutorService;
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
    public ResponseEntity<Autor> criarAutor(@RequestBody Autor autor){
        Autor novoAutor = autorService.criarAutor(autor);
        return new ResponseEntity<>(novoAutor, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Autor>> listarAutores(){
        List<Autor> autores = autorService.listarAutores();
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable Long id){
        return autorService.buscarPorId(id)
                .map(autor -> new ResponseEntity<>(autor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizarAutor(@PathVariable Long id, @RequestBody Autor autor){
        try {
            Autor atualizado = autorService.atualizarAutor(id, autor);
            return new ResponseEntity<>(atualizado, HttpStatus.OK);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id){
        try {
            autorService.deletarAutor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
