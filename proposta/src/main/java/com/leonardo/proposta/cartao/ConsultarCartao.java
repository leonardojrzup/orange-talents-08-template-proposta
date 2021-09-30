package com.leonardo.proposta.cartao;


import com.leonardo.proposta.proposta.*;
import feign.*;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import javax.transaction.*;
import java.util.*;

@Component
public class ConsultarCartao {
    @Autowired
    Tracer tracer;

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    CartaoClient cartaoClient;

    @Scheduled(fixedDelay = 30000)  //Tempo sempre em milisegundos!
    @Transactional
    private void executaOperacao() {

        List<Proposta> propostas = propostaRepository.findByStatusAndCartaoIsNull(StatusProposta.ELEGIVEL);

        if (!propostas.isEmpty()) ;
        for (Proposta proposta : propostas) {
            try {
                CartaoDTO cartaoDTO = cartaoClient.consultar(new CartaoForm(proposta.getDocumento(), proposta.getNome(), proposta.getId()));
                Cartao cartao = cartaoDTO.toModel(proposta);
                proposta.adicionarCartão(cartao);
                propostaRepository.save(proposta);
                Span activeSpan = tracer.activeSpan().setBaggageItem("user.email", cartao.getProposta().getEmail());
            } catch (FeignException feignException) {
                feignException.printStackTrace();
            }
        }
    }
}
