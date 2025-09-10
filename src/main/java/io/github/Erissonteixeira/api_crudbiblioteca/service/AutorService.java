package io.github.Erissonteixeira.api_crudbiblioteca.service;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.mapper.AutorMapper;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    public AutorService(AutorRepository autorRepository, AutorMapper autorMapper) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
    }

    public AutorResponseDTO criarAutor(AutorRequestDTO dto){
        if (dto.getNome() == null || dto.getNome().isBlank()){
            throw new RuntimeException("O Nome do autor não pode ser nulo ou vazio");
        }
        Autor autor = autorMapper.toEntity(dto);
        Autor salvo = autorRepository.save(autor);
        return autorMapper.toDTO(salvo);
    }

    public List<AutorResponseDTO> listarAutores(){
        return autorRepository.findAll()
                .stream()
                .map(autorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AutorResponseDTO> buscarPorId(Long id){
        return autorRepository.findById(id)
                .map(autorMapper::toDTO);
    }

    public AutorResponseDTO atualizarAutor(Long id, AutorRequestDTO dto){
        return autorRepository.findById(id)
                .map(autor -> {
                    autor.setNome(dto.getNome());
                    Autor atualizado = autorRepository.save(autor);
                    return autorMapper.toDTO(atualizado);
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