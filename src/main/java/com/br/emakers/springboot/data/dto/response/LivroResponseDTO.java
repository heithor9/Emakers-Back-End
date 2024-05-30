package com.br.emakers.springboot.data.dto.response;
import com.br.emakers.springboot.data.entity.Livro;

public record LivroResponseDTO(

        int idLivro,

        String nome,

        String autor,

        int data_lancamento

) {
    public LivroResponseDTO(Livro livro) {
        this(livro.getIdLivro(), livro.getNome(), livro.getAutor(), livro.getData_lancamento());
    }
}

