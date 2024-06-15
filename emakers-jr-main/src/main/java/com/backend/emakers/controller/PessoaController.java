package com.backend.emakers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import com.backend.emakers.dto.EmprestimoDTO;
import com.backend.emakers.dto.PessoaDTO;
import com.backend.emakers.model.Pessoa;
import com.backend.emakers.service.PessoaService;

import jakarta.validation.Valid;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private static final Logger logger = Logger.getLogger(PessoaController.class.getName());

    @Autowired
    private PessoaService pessoaService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PessoaDTO> getAllPessoas() {
        logger.log(Level.INFO, "Requisição para buscar todas as pessoas.");
        List<Pessoa> pessoas = pessoaService.findAll();
        logger.log(Level.INFO, "Lista de pessoas: " + pessoas);
        return pessoas.stream()
                .map(PessoaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Integer id) {
        logger.log(Level.INFO, "Requisição para buscar pessoa com ID: " + id);
        Pessoa pessoa = pessoaService.findById(id);
        if (pessoa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PessoaDTO.fromEntity(pessoa));
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPessoa(@Valid @RequestBody PessoaDTO pessoaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }

        Pessoa pessoa = new Pessoa(pessoaDTO.getNome(), pessoaDTO.getCep());
        logger.log(Level.INFO, "Requisição para criar uma nova pessoa: " + pessoa.toString());
        Pessoa savedPessoa = pessoaService.save(pessoa);
        logger.log(Level.INFO, "Pessoa criada com sucesso: " + savedPessoa.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(PessoaDTO.fromEntity(savedPessoa));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updatePessoa(@PathVariable("id") Integer id,
            @Valid @RequestBody PessoaDTO pessoaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }

        Pessoa pessoa = pessoaService.findById(id);
        if (pessoa == null) {
            return ResponseEntity.notFound().build();
        }

        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setCep(pessoaDTO.getCep());

        Pessoa updatedPessoa = pessoaService.save(pessoa);
        logger.log(Level.INFO, "Pessoa atualizada com sucesso: " + updatedPessoa.toString());
        return ResponseEntity.ok(PessoaDTO.fromEntity(updatedPessoa));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePessoa(@PathVariable Integer id) {
        logger.log(Level.INFO, "Requisição para deletar pessoa com ID: " + id);
        Pessoa pessoa = pessoaService.findById(id);
        if (pessoa == null) {
            return ResponseEntity.notFound().build();
        }

        if (!pessoa.getLivros().isEmpty()) {
            logger.log(Level.WARNING,
                    "Não é possível excluir a pessoa " + id + " porque ela possui livros associados.");
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("errors", "Não é possível excluir a pessoa porque ela possui livros associados.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        pessoaService.deleteById(id);
        logger.log(Level.INFO, "Pessoa deletada com sucesso.");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/emprestar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> emprestarLivros(@RequestBody EmprestimoDTO emprestimo) {
        Pessoa pessoa = pessoaService.emprestarLivros(emprestimo.getIdPessoa(), emprestimo.getIdLivro());
        if (pessoa == null) {
            return ResponseEntity.notFound().build();
        }
        logger.log(Level.INFO, "Livros emprestados com sucesso para a pessoa: " + pessoa.toString());
        return ResponseEntity.ok(PessoaDTO.fromEntity(pessoa));
    }

    @PostMapping(value = "/devolver", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> devolverLivros(@RequestBody EmprestimoDTO emprestimo) {
        Pessoa pessoa = pessoaService.devolverLivros(emprestimo.getIdPessoa(), emprestimo.getIdLivro());
        if (pessoa == null) {
            return ResponseEntity.notFound().build();
        }
        logger.log(Level.INFO, "Livros devolvidos com sucesso pela pessoa: " + pessoa.toString());
        return ResponseEntity.ok(PessoaDTO.fromEntity(pessoa));
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
