package com.br.emakers.springboot.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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
    private Category category;


}
