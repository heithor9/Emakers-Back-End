package com.br.emakers.springboot.data.entity;

import com.br.emakers.springboot.data.dto.request.PessoaRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Pessoa")

public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPessoa;

    @Column(name = "name", nullable = false, length = 45)
    private String nome;

    @Column(name = "CÓDIGO DE ENDEREÇAMENTO POSTAL (CEP)")
    private int CEP;

    @Builder
    public Pessoa(PessoaRequestDTO pessoaRequestDTO) {
        this.nome = pessoaRequestDTO.nome();
        this.CEP = pessoaRequestDTO.CEP();
    }
}
