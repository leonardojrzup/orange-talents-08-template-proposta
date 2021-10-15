package com.leonardo.proposta.cartao.carteira;

import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.cartao.CartaoRepository;
import com.leonardo.proposta.cartao.carteira.apiCarteiras.ApiCarteiraDigitalDto;
import com.leonardo.proposta.cartao.carteira.apiCarteiras.ApiCarteiraDigitalForm;
import com.leonardo.proposta.cartao.carteira.apiCarteiras.ApiCateiraDigitalClient;
import feign.FeignException;
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
public class CateiraDigitalController {

    @Autowired
    Tracer tracer;

    @Autowired
    ApiCateiraDigitalClient apiCateiraDigitalClient;

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    CarteiraDigitalRepository carteiraDigitalRepository;



    @PostMapping("/cartoes/{id}/carteiras")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> salvar(@PathVariable Long id, @RequestBody @Valid CarteiraDigitalForm form) {
        Optional<Cartao> cartaoEncontrado = cartaoRepository.findById(id);
        if (cartaoEncontrado.isEmpty()) {
            throw new EntityNotFoundException("Id do cartão não encontrado no banco de dados");
        }
        if (cartaoEncontrado.get().vinculadoCarteira(form.getModeloCarteira().toString()))
            return ResponseEntity.unprocessableEntity().body("Cartão ja está vinculado a carteira " + form.getModeloCarteira());

        try {
            Span activeSpan = tracer.activeSpan().setBaggageItem("user.email", cartaoEncontrado.get().getProposta().getEmail());
            ApiCarteiraDigitalDto response = apiCateiraDigitalClient.consultar(cartaoEncontrado.get().getNumero(), new ApiCarteiraDigitalForm(form.getEmail(), form.getModeloCarteira().toString()));
            if (response.getResultado().equals("ASSOCIADA")) {

                Cartao cartao = cartaoEncontrado.get();
                CarteiraDigital carteira = form.toModel(response.getId(), cartao);
                carteiraDigitalRepository.save(carteira);
                cartao.adicionarCarteira(carteira);
                cartaoRepository.save(cartao);
                URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(carteira.getId())
                        .toUri();
                return ResponseEntity.created(uri).body(new CarteiraDigitalDto(carteira));
            } else {
                return ResponseEntity.unprocessableEntity().body("Houve um erro ao processar a requisição, tente novamente mais tarde");
            }
        } catch (FeignException exception) {
            exception.printStackTrace();
            return ResponseEntity.unprocessableEntity().body("Não foi possivel conectar ao servidor externo, tente novamente mais tarde");
        }
    }
}