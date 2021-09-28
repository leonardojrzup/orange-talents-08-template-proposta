package com.leonardo.proposta.cartao.biometria;

import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.cartao.CartaoRepository;
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
public class BiometriaController {

    @Autowired
    BiometriaRepository biometriaRepository;

    @Autowired
    CartaoRepository cartaoRepository;

    @PostMapping("/cartoes/{id}/biometria")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> cadastrarBiometria(@PathVariable("id") Long id, @Valid @RequestBody BiometriaForm form) {
        Optional<Cartao> cartaoEncontrado = cartaoRepository.findById(id);
        if (cartaoEncontrado.isEmpty())
            throw new EntityNotFoundException("Id do cartão não encontrado no banco de dados");

        else ;
        Cartao cartao = cartaoEncontrado.get();
        Biometria biometria = form.toModel(cartao);
        biometriaRepository.save(biometria);
        cartao.adicionarBiometria(biometria);
        cartaoRepository.save(cartao);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(biometria.getId())
                .toUri();
        return ResponseEntity.created(uri).body(new BiometriaDTO(biometria));

    }

}
