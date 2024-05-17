package com.br.emakers.springboot.Repository;

import com.br.emakers.springboot.data.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
