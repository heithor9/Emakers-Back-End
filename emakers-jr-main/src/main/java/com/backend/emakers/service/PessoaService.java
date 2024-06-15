package com.backend.emakers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.emakers.model.Livro;
import com.backend.emakers.model.Pessoa;
import com.backend.emakers.repository.LivroRepository;
import com.backend.emakers.repository.PessoaRepository;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa findById(Integer id) {
        return pessoaRepository.findById(id).orElse(null);
    }

    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public void deleteById(Integer id) {
        pessoaRepository.deleteById(id);
    }

    public Pessoa emprestarLivros(Integer pessoaId, Integer livroId) {
        Pessoa pessoa = findById(pessoaId);
        if (pessoa == null) {
            return null;
        }

        Livro livro = livroRepository.findById(livroId).orElse(null);
        if (livro == null) {
            return null;
        }

        pessoa.addLivros(livro);
        return pessoaRepository.save(pessoa);
    }

    public Pessoa devolverLivros(Integer pessoaId, Integer livroId) {
        Pessoa pessoa = findById(pessoaId);
        if (pessoa == null) {
            return null;
        }

        Livro livro = livroRepository.findById(livroId).orElse(null);
        if (livro == null) {
            return null;
        }

        pessoa.removeLivro(livroId);
        return pessoaRepository.save(pessoa);
    }
}
