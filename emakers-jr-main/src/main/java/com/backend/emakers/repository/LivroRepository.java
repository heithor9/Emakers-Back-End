package com.backend.emakers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.emakers.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    List<Livro> findLivrosByPessoasId(Long livrosId);
}
