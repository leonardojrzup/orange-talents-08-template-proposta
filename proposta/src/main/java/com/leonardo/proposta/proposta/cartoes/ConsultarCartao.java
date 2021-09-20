package com.leonardo.proposta.proposta.cartoes;


import com.leonardo.proposta.proposta.*;
import feign.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import javax.transaction.*;
import java.util.*;

@Component
public class ConsultarCartao {

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    CartaoClient cartaoClient;

    @Scheduled(fixedDelay = 300000)  //Tempo sempre em milisegundos!
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
            } catch (FeignException feignException) {
                feignException.printStackTrace();
            }
        }
    }
}
