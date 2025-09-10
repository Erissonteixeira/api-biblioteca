package io.github.Erissonteixeira.api_crudbiblioteca.service;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.mapper.LivroMapper;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Livro;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.AutorRepository;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroMapper livroMapper;


    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, LivroMapper livroMapper) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.livroMapper = livroMapper;
    }

    public LivroResponseDTO criarLivro(LivroRequestDTO dto){
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        Livro livro = livroMapper.toEntity(dto);
        livro.setAutor(autor);
        Livro salvo = livroRepository.save(livro);
        return toResponseDTO(salvo);
    }

    public List<LivroResponseDTO> listarLivros(){
        return livroRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<LivroResponseDTO> buscarPorId(Long id){
        return livroRepository.findById(id)
                .map(this::toResponseDTO);
    }

    public LivroResponseDTO atualizarLivro(Long id, LivroRequestDTO dto){
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setTitulo(dto.getTitulo());
                    livro.setAnoPublicacao(dto.getAnoPublicacao());
                    livro.setGenero(dto.getGenero());
                    livro.setStatus(dto.getStatus());

                    Autor autor = autorRepository.findById(dto.getAutorId())
                            .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
                    livro.setAutor(autor);
                    return toResponseDTO(livroRepository.save(livro));
                })
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public void deletarLivro(Long id){
        if (!livroRepository.existsById(id)){
            throw new RuntimeException("Livro não encontrado");
        }
        livroRepository.deleteById(id);
    }


    private LivroResponseDTO toResponseDTO(Livro livro){
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAnoPublicacao(),
                livro.getGenero(),
                livro.getStatus(),
                livro.getAutor().getId(),
                livro.getAutor().getNome()
        );
    }
}