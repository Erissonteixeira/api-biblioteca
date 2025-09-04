package io.github.Erissonteixeira.api_crudbiblioteca.repository;

import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
}
