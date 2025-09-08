package io.github.Erissonteixeira.api_crudbiblioteca.service;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroResponseDTO;
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
    @InjectMocks
    private LivroService livroService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar livro com sucesso")
    void shouldCreateBookSucessfully(){
        LivroRequestDTO dto = new LivroRequestDTO("Dom Casmurro", 1899, "Romance", "Disponivel", 1L);

        Autor autor = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .build();

        Livro livroSalvo = Livro.builder()
                .id(1L)
                .titulo("Dom Casmurro")
                .anoPublicacao(1899)
                .genero("Romance")
                .status("Disponivel")
                .autor(autor)
                .build();

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroRepository.save(any(Livro.class))).thenReturn(livroSalvo);

        LivroResponseDTO response = livroService.criarLivro(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Dom Casmurro", response.getTitulo());
        assertEquals("Machado de Assis", response.getAutorNome());

        verify(autorRepository, times(1)).findById(1L);
        verify(livroRepository, times(1)).save(any(Livro.class));
    }
    @Test
    @DisplayName("Deve listar todos os livros")
    void shouldListAllBooks(){
        Livro livro1 = Livro.builder()
                .id(1L)
                .titulo("Dom Casmurro")
                .anoPublicacao(1899)
                .genero("Romance")
                .status("Disponivel")
                .autor(Autor.builder().id(1L).nome("Machado de Assis").build())
                .build();

        Livro livro2 = Livro.builder().id(2L)
                .titulo("Memórias Póstumas de Brás Cubas")
                .anoPublicacao(1881)
                .genero("Romance")
                .status("Disponivel")
                .autor(Autor.builder().id(1L).nome("Machado de Assis").build())
                .build();

        when(livroRepository.findAll()).thenReturn(List.of(livro1, livro2));

        List<LivroResponseDTO> responseList = livroService.listarLivros();

        assertNotNull(responseList, "A lista de livros não deve ser nula");
        assertEquals(2, responseList.size(), "Deve retornar exatamente 2 livros");
        assertEquals("Dom Casmurro", responseList.get(0).getTitulo(), "Título do primeiro livro incorreto");
        assertEquals("Memórias Póstumas de Brás Cubas", responseList.get(1).getTitulo(), "Título do segundo livro incorreto");
        verify(livroRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("Deve retornar livro pelo id")
    void shouldReturnBookById(){
        Livro livro = Livro.builder()
                .id(1L)
                .titulo("Dom Casmurro")
                .anoPublicacao(1899)
                .genero("Romance")
                .status("Disponivel")
                .autor(Autor.builder().id(1L).nome("Machado de Assis").build())
                .build();

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        Optional<LivroResponseDTO> response = livroService.buscarPorId(1L);

        assertTrue(response.isPresent(), "O livro deve ser encontrado");
        assertEquals(1L, response.get().getId(), "ID do livro incorreto");
        assertEquals("Dom Casmurro", response.get().getTitulo(), "Título do livro incorreto");
        assertEquals("Machado de Assis", response.get().getAutorNome(), "Nome do autor incorreto");

        verify(livroRepository, times(1)).findById(1l);
    }
}
