package com.backend.emakers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.emakers.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    List<Pessoa> findPessoasByLivrosId(Long livroId);
}