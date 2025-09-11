package io.github.Erissonteixeira.api_crudbiblioteca.service;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.mapper.LivroMapper;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Livro;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.AutorRepository;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private LivroService livroService;

    private Autor autor;
    private Livro livro;
    private LivroRequestDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        autor = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .build();


        dto = new LivroRequestDTO("Dom Casmurro", 1899, "Romance", "Disponivel", 1L);


        livro = Livro.builder()
                .id(1L)
                .titulo("Dom Casmurro")
                .anoPublicacao(1899)
                .genero("Romance")
                .status("Disponivel")
                .autor(autor)
                .build();
    }

    @Test
    @DisplayName("Deve criar livro com sucesso usando o mapper")
    void shouldCreateBookSuccessfully() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroMapper.toEntity(dto)).thenReturn(livro);
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        LivroResponseDTO response = livroService.criarLivro(dto);

        assertNotNull(response);
        assertEquals("Dom Casmurro", response.getTitulo());
        assertEquals("Machado de Assis", response.getAutorNome());

        verify(autorRepository, times(1)).findById(1L);
        verify(livroRepository, times(1)).save(any(Livro.class));
        verify(livroMapper, times(1)).toEntity(dto);
    }

    @Test
    @DisplayName("Deve listar todos os livros usando mapper")
    void shouldListAllBooks() {
        Livro livro2 = Livro.builder()
                .id(2L)
                .titulo("Memórias Póstumas de Brás Cubas")
                .anoPublicacao(1881)
                .genero("Romance")
                .status("Disponivel")
                .autor(autor)
                .build();

        when(livroRepository.findAll()).thenReturn(List.of(livro, livro2));
        when(livroMapper.toDTO(livro)).thenCallRealMethod();
        when(livroMapper.toDTO(livro2)).thenCallRealMethod();

        List<LivroResponseDTO> responseList = livroService.listarLivros();

        assertEquals(2, responseList.size());
        assertEquals("Dom Casmurro", responseList.get(0).getTitulo());
        assertEquals("Memórias Póstumas de Brás Cubas", responseList.get(1).getTitulo());
        verify(livroRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar livro pelo id usando mapper")
    void shouldReturnBookById() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(livroMapper.toDTO(livro)).thenCallRealMethod();

        Optional<LivroResponseDTO> response = livroService.buscarPorId(1L);

        assertTrue(response.isPresent());
        assertEquals("Dom Casmurro", response.get().getTitulo());
        assertEquals("Machado de Assis", response.get().getAutorNome());
        verify(livroRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar livro existente com sucesso usando mapper")
    void shouldUpdateBookSuccessfully() {
        LivroRequestDTO updateDto = new LivroRequestDTO("Dom Casmurro Atualizado", 1900, "Romance", "Emprestado", 1L);
        Livro livroAtualizado = Livro.builder()
                .id(1L)
                .titulo("Dom Casmurro Atualizado")
                .anoPublicacao(1900)
                .genero("Romance")
                .status("Emprestado")
                .autor(autor)
                .build();

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroRepository.save(any(Livro.class))).thenReturn(livroAtualizado);
        when(livroMapper.toDTO(livroAtualizado)).thenCallRealMethod();

        LivroResponseDTO response = livroService.atualizarLivro(1L, updateDto);

        assertEquals("Dom Casmurro Atualizado", response.getTitulo());
        assertEquals(1900, response.getAnoPublicacao());
        assertEquals("Emprestado", response.getStatus());
        assertEquals("Machado de Assis", response.getAutorNome());

        verify(livroRepository, times(1)).findById(1L);
        verify(autorRepository, times(1)).findById(1L);
        verify(livroRepository, times(1)).save(any(Livro.class));
    }
    }
