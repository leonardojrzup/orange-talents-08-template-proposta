package com.leonardo.proposta.proposta.cartao.bloqueio;


import com.leonardo.proposta.proposta.cartao.Cartao;
import com.leonardo.proposta.proposta.cartao.CartaoRepository;
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
public class BloqueioController {

    @Autowired
    BloqueioRepository bloqueioRepository;

    @Autowired
    CartaoRepository cartaoRepository;


    @PostMapping("/cartoes/{id}/bloqueio")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> bloquearCartao(@PathVariable("id") Long id, @Valid @RequestBody BloqueioForm form) {

        System.out.println(form.getClass());
        Optional<Cartao> cartaoEncontrado = cartaoRepository.findById(id);
        if (cartaoEncontrado.isEmpty()) {
            throw new EntityNotFoundException("Id do cartão não encontrado no banco de dados");
        } else {
            Cartao cartao = cartaoEncontrado.get();
            Bloqueio bloqueio = form.toModel(cartao);
            bloqueioRepository.save(bloqueio);
            cartao.adicionarBloqueio(bloqueio);
            cartaoRepository.save(cartao);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(bloqueio.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(new BloqueioDTO(bloqueio));

        }
    }
}