package io.github.Erissonteixeira.api_crudbiblioteca.dto;

import jakarta.validation.constraints.*;

public class LivroRequestDTO {
    @NotBlank(message = "O titulo do livro é obrigatório")
    @Size(max = 100, message = "Máx 100 caracteres")
    private String titulo;
    @NotNull(message = "O ano de publicação é obrigatório")
    @Min(value = 1990, message = "O ano de publicação deve ser maior ou igual a 1990")
    @Max(value = 2025, message = "O ano de publicação não pode ser no futuro")
    private Integer anoPublicacao;
    @NotBlank(message = "O gênero do livro é obrigatório")
    @Size(max = 50, message = "Máx. 50 caracteres")
    private String genero;
    @NotBlank(message = "O status do livro é obrigatório")
    private String status;
    @NotNull(message = "O autor é obrigatório")
    private Long autorId;

    public LivroRequestDTO(){
    }

    public LivroRequestDTO(String titulo, Integer anoPublicacao, String genero, String status, Long autorId){
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.status = status;
        this.autorId = autorId;
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
}
