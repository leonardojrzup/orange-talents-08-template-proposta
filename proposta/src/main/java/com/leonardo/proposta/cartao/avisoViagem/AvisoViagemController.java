package com.leonardo.proposta.cartao.avisoViagem;

import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.cartao.CartaoRepository;
import com.leonardo.proposta.cartao.avisoViagem.apiAvisoViagem.ApiAvisoDto;
import com.leonardo.proposta.cartao.avisoViagem.apiAvisoViagem.ApiAvisoForm;
import com.leonardo.proposta.cartao.avisoViagem.apiAvisoViagem.ApiAvisoViagemLegadoClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AvisoViagemController {


    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AvisoViagemRepository avisoViagemRepository;

    @Autowired
    ApiAvisoViagemLegadoClient apiAvisoViagemLegadoClient;

    @PostMapping("/cartoes/{id}/viagens")
    @Transactional
    public ResponseEntity<?> salvar(@PathVariable("id") Long id, @RequestBody @Valid AvisoViagemForm form) {
        Optional<Cartao> cartaoEncontrado = cartaoRepository.findById(id);
        if (cartaoEncontrado.isEmpty()) {
            throw new EntityNotFoundException("Id do cartão não encontrado no banco de dados");
        }


        try {
            ApiAvisoDto response = apiAvisoViagemLegadoClient.informarAviso(cartaoEncontrado.get().getNumero(), new ApiAvisoForm(form.getDestinoViagem(), form.getTerminoViagem()));


            if (response.getResultado().equals("CRIADO")) {
                Cartao cartao = cartaoEncontrado.get();
                AvisoViagem viagem = form.toModel(cartao);
                avisoViagemRepository.save(viagem);
                cartao.adicionarViagem(viagem);
                cartaoRepository.save(cartao);
                return ResponseEntity.ok().body(new AvisoViagemDto(viagem));

            } else {
                return ResponseEntity.unprocessableEntity().body("Não foi possivel conectar ao servidor externo, tente novamente mais tarde");
            }
        } catch (FeignException exception) {

           exception.printStackTrace();
            return ResponseEntity.unprocessableEntity().body("Não foi possivel conectar ao servidor externo, tente novamente mais tarde");
        }
    }
}

