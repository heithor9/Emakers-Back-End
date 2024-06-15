package com.backend.emakers.dto;

import java.util.HashSet;
import java.util.Set;

import com.backend.emakers.model.Livro;
import com.backend.emakers.model.Pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PessoaDTO {
    private Integer idPessoa;

    @NotBlank(message = "O campo nome não pode ser vazio.")
    @Size(min = 2, max = 80, message = "O comprimento do nome deve ter entre 2 e 80 caracteres.")
    private String nome;

    @NotBlank(message = "O campo CEP não pode ser vazio.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "Formato de CEP inválido. Use o formato XXXXX-XXX")
    private String cep;

    private Set<Livro> livros;

    // Construtor vazio necessário para frameworks como Spring
    public PessoaDTO() {
    }

    // Construtor com campos
    public PessoaDTO(Integer idPessoa, String nome, String cep) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.cep = cep;
        this.livros = new HashSet<>();
    }

    // Método estático para converter de Pessoa para PessoaDTO
    public static PessoaDTO fromEntity(Pessoa pessoa) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setIdPessoa(pessoa.getId());
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setCep(pessoa.getCep());
        pessoaDTO.setLivros(pessoa.getLivros());
        return pessoaDTO;
    }

    // Getters e Setters
    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Set<Livro> livros) {
        this.livros = livros;
    }
}
