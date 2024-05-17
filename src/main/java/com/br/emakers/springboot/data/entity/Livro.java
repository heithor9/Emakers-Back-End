package com.br.emakers.springboot.data.entity;

import com.br.emakers.springboot.data.dto.request.LivroRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Livro")

public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLivro;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Column(name ="autor", nullable = false, length = 45)
    private String autor;

    @Column(name = "data_lancamento")
    private int data_lancamento;

    @ManyToOne()
    @JoinColumn(name = "idCategory")
    private Pessoa pessoa;

    @Builder
    public Livro(LivroRequestDTO livroRequestDTO) {
        this.nome = livroRequestDTO.nome();
        this.pessoa = livroRequestDTO.pessoa();
        this.data_lancamento = livroRequestDTO.data_lancamento();
        this.autor = livroRequestDTO.autor();
    }
}
