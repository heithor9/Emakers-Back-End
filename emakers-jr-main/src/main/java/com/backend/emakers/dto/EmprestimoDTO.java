package com.backend.emakers.dto;

import jakarta.validation.constraints.NotBlank;

public class EmprestimoDTO {

    @NotBlank(message = "O idLivro nome não pode ser vazio.")
    private Integer idLivro;
    @NotBlank(message = "O idPessoa nome não pode ser vazio.")
    private Integer idPessoa;

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }
}
