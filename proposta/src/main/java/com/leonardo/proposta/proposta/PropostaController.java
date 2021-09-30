package com.leonardo.proposta.proposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leonardo.proposta.excecao.RegistroDuplicadoException;
import com.leonardo.proposta.metricas.PropostaMetricas;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosClient;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    Tracer tracer;

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    DadosFinanceirosClient dadosFinanceirosClient;

    @Autowired
    PropostaMetricas propostaMetricas;


    @GetMapping("/{id}")
    public ResponseEntity<PropostaDTO> detalharProposta(@PathVariable("id") Long id) {
        Optional<Proposta> proposta = propostaRepository.findById(id);
        if (proposta.isPresent()) {
            Proposta propostaSalva = proposta.get();
            return ResponseEntity.status(HttpStatus.OK).body(new PropostaDTO(propostaSalva));
        }else{
            throw new EntityNotFoundException("ID da proposta não encontrado no banco de dados.");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> salvarProposta(@RequestBody @Valid PropostaForm form) throws JsonProcessingException {
        Proposta proposta = form.toModel();

        if (!proposta.isUnique(propostaRepository)) {
            throw new RegistroDuplicadoException("Documento", "Já existe uma proposta em andamento para o documento informando");
        }
        propostaRepository.save(proposta);
        proposta.verificaSituacaoFinanceira(dadosFinanceirosClient);
        propostaRepository.save(proposta);
        propostaMetricas.contador();
        Span activeSpan = tracer.activeSpan().setBaggageItem("user.email", proposta.getEmail());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
