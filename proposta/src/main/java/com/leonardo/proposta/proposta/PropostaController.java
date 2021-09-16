package com.leonardo.proposta.proposta;

import com.leonardo.proposta.excecao.RegistroDuplicadoException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.net.*;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    PropostaRepository propostaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> salvarProposta(@RequestBody @Valid PropostaForm form){
    Proposta proposta = form.toModel();

        if (!proposta.isUnique(propostaRepository)) {
            throw new RegistroDuplicadoException("Documento", "Já existe uma proposta em andamento para o documento informando");
        }

    propostaRepository.save(proposta);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
