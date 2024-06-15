package com.backend.emakers.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 80)
    private String nome;

    @Column(length = 9)
    private String cep;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "pessoa_livro",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id"))

    private Set<Livro> livros = new HashSet<>();

    public Pessoa() {
    }

    public Pessoa(String nome, String cep) {
        this.nome = nome;
        this.cep = cep;
    }

    // Getters e Setters

    public Integer getId() { // Getter para obter o ID da pessoa
        return id;
    }

    public void setIdPessoa(Integer id) { // Setter para definir o ID da pessoa
        this.id = id;
    }

    public String getNome() { // Getter para obter o nome da pessoa
        return nome;
    }

    public void setNome(String nome) { // Setter para definir o nome da pessoa
        this.nome = nome;
    }

    public String getCep() { // Getter para obter o CEP da pessoa
        return cep;
    }

    public void setCep(String cep) { // Setter para definir o CEP da pessoa
        this.cep = cep;
    }

    public Set<Livro> getLivros() { // Getter para obter o conjunto de livros associados à pessoa
        return livros;
    }

    public void addLivros(Livro livro) {
        this.livros.add(livro);
        livro.getPessoas().add(this);
    }

    public void removeLivro(long livroId) {
        Livro livro = this.livros.stream().filter(t -> t.getId() == livroId).findFirst().orElse(null);
        if (livro != null) {
            this.livros.remove(livro);
            livro.getPessoas().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cep='" + cep + '\'' +
                ", livros=" + livros +
                '}';
    }
}
