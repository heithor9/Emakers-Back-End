package com.backend.emakers.controller;

import com.backend.emakers.dto.LivroDTO;
import com.backend.emakers.model.Livro;
import com.backend.emakers.service.LivroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.logging.Logger;
import java.util.logging.Level;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private static final Logger logger = Logger.getLogger(LivroController.class.getName());

    @Autowired
    private LivroService livroService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LivroDTO> getAllLivros() {
        logger.log(Level.INFO, "Requisição para buscar todos os livros.");
        List<Livro> livros = livroService.findAll();
        return livros.stream()
                .map(LivroDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LivroDTO> getLivroById(@PathVariable Integer id) {
        logger.log(Level.INFO, "Requisição para buscar livro com ID: " + id);
        Livro livro = livroService.findById(id);
        if (livro == null) {
            logger.log(Level.WARNING, "Livro não encontrado para o ID: " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(LivroDTO.fromEntity(livro));
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createLivro(@Valid @RequestBody LivroDTO livroDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }

        Livro livro = new Livro();
        livro.setAutor(livroDTO.getAutor());
        livro.setNome(livroDTO.getNome());
        livro.setDataLancamento(livroDTO.getDataLancamento());
        logger.log(Level.INFO, "Requisição para criar um novo livro: " + livro.toString());
        Livro savedLivro = livroService.save(livro);
        logger.log(Level.INFO, "Livro criado com sucesso: " + savedLivro.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(LivroDTO.fromEntity(savedLivro));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateLivro(@PathVariable("id") Integer id, @Valid @RequestBody LivroDTO livroDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }

        Livro livro = livroService.findById(id);
        if (livro == null) {
            logger.log(Level.WARNING, "Livro não encontrado para o ID: " + id);
            return ResponseEntity.notFound().build();
        }

        livro.setNome(livroDTO.getNome());
        livro.setAutor(livroDTO.getAutor());
        livro.setDataLancamento(livroDTO.getDataLancamento());

        Livro updatedLivro = livroService.save(livro);
        logger.log(Level.INFO, "Livro atualizado com sucesso: " + updatedLivro.toString());
        return ResponseEntity.ok(LivroDTO.fromEntity(updatedLivro));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLivro(@PathVariable Integer id) {
        logger.log(Level.INFO, "Requisição para deletar livro com ID: " + id);
        Livro livro = livroService.findById(id);
        if (livro == null) {
            logger.log(Level.WARNING, "Livro não encontrado para o ID: " + id);
            return ResponseEntity.notFound().build();
        }

        if (!livro.getPessoas().isEmpty()) {
            logger.log(Level.WARNING, "Não é possível excluir o livro " + id + " porque ele está associado a pessoas.");
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("errors", "Não é possível excluir o livro porque ele está associado a pessoas.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        livroService.deleteById(id);
        logger.log(Level.INFO, "Livro deletado com sucesso.");
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, Object>> handleValidationErrors(BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, String> errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }
}
