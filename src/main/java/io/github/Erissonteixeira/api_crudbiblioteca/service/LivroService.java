package io.github.Erissonteixeira.api_crudbiblioteca.service;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Autor;
import io.github.Erissonteixeira.api_crudbiblioteca.model.Livro;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.AutorRepository;
import io.github.Erissonteixeira.api_crudbiblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository  livroRepository;
    private final AutorRepository  autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }
    public Livro criarLivro(Livro livro){
        Long autorId = livro.getAutor().getId();
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        livro.setAutor(autor);
        return livroRepository.save(livro);
    }
    public List<Livro> listarLivros(){
        return livroRepository.findAll();
    }
    public Optional<Livro> buscarPorId(Long id){
        return livroRepository.findById(id);
    }
    public Livro atualizarLivro(Long id, Livro livroAtualizado){
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
                    livro.setGenero(livroAtualizado.getGenero());
                    livro.setStatus(livroAtualizado.getStatus());
                    Long autorId = livroAtualizado.getAutor().getId();
                    Autor autor = autorRepository.findById(autorId)
                            .orElseThrow(()-> new RuntimeException("Autor não encontrado"));
                    livro.setAutor(autor);
                    return livroRepository.save(livro);
                })
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }
    public void deletarLivro(Long id){
        if (!livroRepository.existsById(id)){
            throw new RuntimeException("Livro não encontrado");
        }
        livroRepository.deleteById(id);
    }
}
