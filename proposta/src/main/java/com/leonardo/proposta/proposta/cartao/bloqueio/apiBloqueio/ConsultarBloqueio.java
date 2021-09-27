package com.leonardo.proposta.proposta.cartao.bloqueio.apiBloqueio;


import com.leonardo.proposta.proposta.cartao.Cartao;
import com.leonardo.proposta.proposta.cartao.CartaoRepository;
import com.leonardo.proposta.proposta.cartao.StatusCartao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ConsultarBloqueio {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    ApiBloqueioLegadoClient apiBloqueioLegadoClient;

    @Scheduled(fixedDelay = 30000)  //Tempo sempre em milisegundos!
    @Transactional
    private void executaOperacao() {

        List<Cartao> cartoesParaSeremAtualizados = cartaoRepository.findByStatusCartao(StatusCartao.PEDIDO_RECEBIDO);

        if (!cartoesParaSeremAtualizados.isEmpty()) {
            for (Cartao cartao : cartoesParaSeremAtualizados) {
                try {
                    ApiBloqueioResponse response = apiBloqueioLegadoClient.bloquear(cartao.getNumero(), new ApiBloqueioForm("proposta"));

                    if (response.getResultado().equals("BLOQUEADO"))
                        cartao.setStatusCartao(StatusCartao.BLOQUEADO);
                    cartaoRepository.save(cartao);
                    System.out.println(cartao.getId() + " bloqueado");


                } catch (FeignException feignException) {
                    cartao.setStatusCartao(StatusCartao.DESBLOQUEADO);
                    cartaoRepository.save(cartao);
                    System.out.println(cartao.getId() + " Não Bloqueado");
                    System.out.println(feignException.getMessage());
                    System.out.println(feignException.responseBody());
                    //    feignException.printStackTrace();
                }
            }
        }
    }
}
