package io.github.Erissonteixeira.api_crudbiblioteca.mapper;

import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorRequestDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.dto.AutorResponseDTO;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorRequestDTO dto);

    default AutorResponseDTO toDTO(Autor autor){
        if (autor == null) return null;
        return new AutorResponseDTO(autor.getId(), autor.getNome());
    }
}
