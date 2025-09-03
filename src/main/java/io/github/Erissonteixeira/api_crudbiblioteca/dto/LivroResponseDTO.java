package io.github.Erissonteixeira.api_crudbiblioteca.dto;

public class LivroResponseDTO {
    private Long id;
    private String titulo;
    private Integer anoPublicacao;
    private String genero;
    private String status;
    private Long autorId;
    private String autorNome;

    public LivroResponseDTO(){
    }

    public LivroResponseDTO(Long id, String titulo, Integer anoPublicacao, String genero, String status, Long autorId, String autorNome){
        this.id = id;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.status = status;
        this.autorId = autorId;
        this.autorNome = autorNome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getAutorNome() {
        return autorNome;
    }

    public void setAutorNome(String autorNome) {
        this.autorNome = autorNome;
    }
}
