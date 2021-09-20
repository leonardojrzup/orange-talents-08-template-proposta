package com.leonardo.proposta.proposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leonardo.proposta.excecao.RegistroDuplicadoException;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosClient;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.*;
import javax.validation.constraints.*;
import java.net.*;
import java.util.List;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    DadosFinanceirosClient dadosFinanceirosClient;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> salvarProposta(@RequestBody @Valid PropostaForm form) throws JsonProcessingException {
    Proposta proposta = form.toModel();

        if (!proposta.isUnique(propostaRepository)) {
            throw new RegistroDuplicadoException("Documento", "Já existe uma proposta em andamento para o documento informando");
        }

        propostaRepository.save(proposta);//Precisa gerar o id para realizar a consulta na API
        proposta.verificaSituacaoFinanceira(dadosFinanceirosClient);

        propostaRepository.save(proposta);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
