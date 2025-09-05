package io.github.Erissonteixeira.api_crudbiblioteca.service;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.AutorRepository;
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

public class AutorServiceTest {
    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Deve criar autor com sucesso")
    void shouldCreateAuthorSuccessfully(){
        AutorRequestDTO dto = new AutorRequestDTO("Machado de Assis");
        Autor autorSalvo = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .build();

        when(autorRepository.save(any(Autor.class))).thenReturn(autorSalvo);

        AutorResponseDTO response = autorService.criarAutor(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Machado de Assis", response.getNome());
        verify(autorRepository, times(1)).save(any(Autor.class));
    }
    @Test
    @DisplayName("Deve listar todos os autores")
    void shouldListAllAuthors(){
        Autor autor1 = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .build();
        Autor autor2 = Autor.builder()
                .id(2L)
                .nome("Clarice Lispector")
                .build();

        when(autorRepository.findAll()).thenReturn(List.of(autor1, autor2));

        List<AutorResponseDTO> responseList = autorService.listarAutores();

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals("Machado de Assis", responseList.get(0).getNome());
        assertEquals("Clarice Lispector", responseList.get(1).getNome());
        verify(autorRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("Deve lançar exceção ao criar autor com nome nulo")
    void shouldthrowExceptionWhenAuthorNameIsNull(){
        AutorRequestDTO dto = new AutorRequestDTO(null);

        assertThrows(NullPointerException.class, () -> autorService.criarAutor(dto));

        verify(autorRepository, never()).save(any(Autor.class));
    }
    @Test
    @DisplayName("Deve retornar autor pelo id")
    void shouldReturnAuthorById(){
        Autor autor = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .build();

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        Optional<AutorResponseDTO> response = autorService.buscarPorId(1L);

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
        assertEquals("Machado de Assis", response.get().getNome());
        verify(autorRepository, times(1)).findById(1l);
    }
    @Test
    @DisplayName("Deve Atualizar autor existente com sucesso")
    void shouldUpdateAuthorSucessfully(){
        Autor autorExistente = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .build();

        AutorRequestDTO dto = new AutorRequestDTO("Machado de Assis");

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autorExistente));

        Autor autorAtualizado = Autor.builder()
                .id(1L)
                .nome("Machado de Assis Atualizado")
                .build();

        when(autorRepository.save(any(Autor.class))).thenReturn(autorAtualizado);

        AutorResponseDTO response = autorService.atualizarAutor(1L, dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Machado de Assis Atualizado", response.getNome());

        verify(autorRepository, times(1)).findById(1l);
        verify(autorRepository, times(1)).save(any(Autor.class));
    }
    @Test
    @DisplayName("Deve deletar autor existente com sucesso")
    void shouldDeleteAuthorSuccessfully(){
        when(autorRepository.existsById(1L)).thenReturn(true);

        autorService.deletarAutor(1L);

        verify(autorRepository, times(1)).deleteById(1l);

    }
    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar autor inexistente")
    void shouldThrowExceptionWhenDeletingNonExistingAuthor(){
        when(autorRepository.existsById(1l)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> autorService.deletarAutor(1l));

        verify(autorRepository, never()).deleteById(anyLong());
    }

}
