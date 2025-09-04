package io.github.Erissonteixeira.api_crudbiblioteca.service;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor criarAutor(Autor autor){
        return autorRepository.save(autor);
    }
    public List<Autor> listarAutores(){
        return autorRepository.findAll();
    }
    public Optional<Autor> buscarPorId(Long id){
        return autorRepository.findById(id);
    }
    public Autor atualizarAutor(Long id, Autor autorAtualizado){
        return autorRepository.findById(id)
                .map(autor -> {
                    autor.setNome(autorAtualizado.getNome());
                    return autorRepository.save(autor);
                })
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
    }
    public void deletarAutor(Long id){
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("Autor não encontrado");
        }
        autorRepository.deleteById(id);
    }
}
