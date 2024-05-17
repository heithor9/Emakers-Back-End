package com.br.emakers.springboot.data.dto.request;
import jakarta.validation.constraints.NotBlank;
public record PessoaRequestDTO(

        @NotBlank(message = "Name is required")
        String nome,

        @NotBlank(message = "CEP is required")
        int CEP
) {
}

