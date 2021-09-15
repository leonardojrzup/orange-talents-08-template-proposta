package com.leonardo.proposta.proposta;

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
    public ResponseEntity<?> salvarProposta(@RequestBody @Valid PropostaForm form){
        System.out.println(form.getDocumento());
        System.out.println(form.getNome());



    Proposta proposta = form.toModel();
    propostaRepository.save(proposta);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
