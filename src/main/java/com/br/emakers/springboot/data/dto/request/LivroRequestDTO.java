package com.br.emakers.springboot.data.dto.request;

import com.br.emakers.springboot.data.entity.Pessoa;
import jakarta.validation.constraints.NotBlank;


public record LivroRequestDTO(

        @NotBlank(message = "Name is required")
        String nome,

        @NotBlank(message = "Author is required")
        String autor,

        @NotBlank(message = "Release data is required")
        int data_lancamento,

        Pessoa pessoa)
{
}
