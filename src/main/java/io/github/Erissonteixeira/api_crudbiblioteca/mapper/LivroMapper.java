package io.github.Erissonteixeira.api_crudbiblioteca.mapper;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.LivroRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Livro;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LivroMapper {

    Livro toEntity(LivroRequestDTO dto);

    default  LivroRequestDTO toDTO(Livro livro){
        if (livro == null) return null;
        LivroRequestDTO dto = new LivroRequestDTO();
        dto.setTitulo(livro.getTitulo());
        dto.setAnoPublicacao(livro.getAnoPublicacao());
        dto.setGenero(livro.getGenero());
        dto.setStatus(livro.getStatus());
        if (livro.getAutor() != null){
            dto.setAutorId(livro.getAutor().getId());
        }
        return dto;
    }

}
