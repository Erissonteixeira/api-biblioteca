package io.github.Erissonteixeira.api_crudbiblioteca.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AutorRequestDTO {
    @NotBlank(message = "O nome do autor é obrigatório")
    @Size(max = 30, message ="Máx. 30 Caracteres")
    private String nome;

    public AutorRequestDTO(){
    }

    public AutorRequestDTO(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
