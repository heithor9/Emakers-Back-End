package com.br.emakers.springboot.service;

import com.br.emakers.springboot.Repository.PessoaRepository;
import com.br.emakers.springboot.data.dto.request.PessoaRequestDTO;
import com.br.emakers.springboot.data.dto.response.PessoaResponseDTO;
import com.br.emakers.springboot.data.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaResponseDTO> getAllPessoas(){
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream().map(PessoaResponseDTO::new).collect(Collectors.toList());
    }

    public PessoaResponseDTO getPessoaById(Long idPessoa){
        Pessoa pessoa = getPessoaEntityById(idPessoa);
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO createPessoa(PessoaRequestDTO pessoaRequestDTO){
        Pessoa pessoa = new Pessoa(pessoaRequestDTO);
        pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO updatePessoa(Long idPessoa, PessoaRequestDTO pessoaRequestDTO){
        Pessoa pessoa = getPessoaEntityById(idPessoa);
        pessoa.setNome(pessoaRequestDTO.nome());
        pessoaRepository.save(pessoa);
        return new PessoaResponseDTO(pessoa);

    }

    public String deletePessoa(Long idPessoa){
        Pessoa pessoa = getPessoaEntityById(idPessoa);
        pessoaRepository.delete(pessoa);
        return "Pessoa iD: " + idPessoa + " deleted!";
    }

    private Pessoa getPessoaEntityById(Long idPessoa){
        return pessoaRepository.findById(idPessoa).orElseThrow(()-> new RuntimeException("Categoria não encontrada"));
    }

}
