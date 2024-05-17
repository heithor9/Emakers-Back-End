package com.br.emakers.springboot.data.dto.response;

import com.br.emakers.springboot.data.entity.Pessoa;

public record PessoaResponseDTO(

        Long idPessoa,

        String nome,

        int CEP
) {
    public PessoaResponseDTO(Pessoa pessoa) {
        this(pessoa.getIdPessoa(), pessoa.getNome(), pessoa.getCEP());
    }
}
