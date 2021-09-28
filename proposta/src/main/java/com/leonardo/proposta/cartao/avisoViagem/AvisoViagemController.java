package com.leonardo.proposta.cartao.avisoViagem;

import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.cartao.CartaoRepository;
import com.leonardo.proposta.cartao.bloqueio.BloqueioDTO;
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
public class AvisoViagemController {


    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AvisoViagemRepository avisoViagemRepository;

    @PostMapping("/cartoes/{id}/viagens")
    @Transactional
    public ResponseEntity<?> salvar(@PathVariable("id") Long id, @RequestBody @Valid AvisoViagemForm form) {
        Optional<Cartao> cartaoEncontrado = cartaoRepository.findById(id);
        if (cartaoEncontrado.isEmpty()) {
            throw new EntityNotFoundException("Id do cartão não encontrado no banco de dados");
        }

        Cartao cartao = cartaoEncontrado.get();
        AvisoViagem viagem = form.toModel(cartao);
        avisoViagemRepository.save(viagem);
        cartao.adicionarViagem(viagem);
        cartaoRepository.save(cartao);


        return ResponseEntity.ok().body(new AvisoViagemDto(viagem));


    }

}

