package com.leonardo.proposta.proposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardo.proposta.excecao.RegistroDuplicadoException;
import com.leonardo.proposta.metricas.PropostaMetricas;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosClient;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosDTO;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosForm;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


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
        Span activeSpan = tracer.activeSpan().setBaggageItem("user.email", proposta.getEmail());
        activeSpan.log("Email");

        propostaRepository.save(proposta);
        verificaSituacaoFinanceira(proposta);
        propostaRepository.save(proposta);
        propostaMetricas.contador();

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }




    public void verificaSituacaoFinanceira(Proposta proposta) throws JsonProcessingException {
        DadosFinanceirosForm request = new DadosFinanceirosForm(proposta);
        DadosFinanceirosDTO response;
        try {
            response = dadosFinanceirosClient.consultar(request);
        }catch (FeignException exception) {
            //exception.printStackTrace();
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(exception.contentUTF8(), DadosFinanceirosDTO.class);
            logger.error("Retorno com erro da API de avaliação financeira"
                    +exception.getCause().toString());
        }
        proposta.setStatus(StatusProposta.converter(response.getResultadoSolicitacao()));
    }
}
