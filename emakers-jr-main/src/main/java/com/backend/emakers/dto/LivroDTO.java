package com.backend.emakers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

import com.backend.emakers.model.Livro;

public class LivroDTO {

    private Integer idLivro;

    @NotBlank(message = "O campo nome não pode ser vazio.")
    @Size(min = 2, max = 45, message = "O comprimento do nome deve ter entre 2 e 45 caracteres.")
    private String nome;

    @NotBlank(message = "O campo autor não pode ser vazio.")
    @Size(min = 2, max = 45, message = "O comprimento do autor deve ter entre 2 e 45 caracteres.")
    private String autor;

    private Date dataLancamento;

    // Método para converter Livro para LivroDTO
    public static LivroDTO fromEntity(Livro livro) {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setIdLivro(livro.getId());
        livroDTO.setNome(livro.getNome());
        livroDTO.setAutor(livro.getAutor());
        livroDTO.setDataLancamento(livro.getDataLancamento());
        return livroDTO;
    }

    // Getters e Setters

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
}
