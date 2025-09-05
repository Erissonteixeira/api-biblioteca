package io.github.Erissonteixeira.api_crudbiblioteca.service;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public AutorResponseDTO criarAutor(AutorRequestDTO dto){
        if (dto.getNome() == null || dto.getNome().isBlank()){
            throw new NullPointerException("O Nome do autor não pode ser nulo ou vazio");
        }
        Autor autor = new Autor(dto.getNome());
        Autor salvo = autorRepository.save(autor);
        return new AutorResponseDTO(salvo.getId(), salvo.getNome());
    }

    public List<AutorResponseDTO> listarAutores(){
        return autorRepository.findAll()
                .stream()
                .map(a -> new AutorResponseDTO(a.getId(), a.getNome()))
                .collect(Collectors.toList());
    }

    public Optional<AutorResponseDTO> buscarPorId(Long id){
        return autorRepository.findById(id)
                .map(a -> new AutorResponseDTO(a.getId(), a.getNome()));
    }

    public AutorResponseDTO atualizarAutor(Long id, AutorRequestDTO dto){
        return autorRepository.findById(id)
                .map(autor -> {
                    autor.setNome(dto.getNome());
                    Autor atualizado = autorRepository.save(autor);
                    return new AutorResponseDTO(atualizado.getId(), atualizado.getNome());
                })
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
    }

    public void deletarAutor(Long id){
        if (!autorRepository.existsById(id)){
            throw new RuntimeException("Autor não encontrado");
        }
        autorRepository.deleteById(id);
    }
}