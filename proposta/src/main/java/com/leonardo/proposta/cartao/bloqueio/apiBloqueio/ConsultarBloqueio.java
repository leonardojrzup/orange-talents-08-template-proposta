package com.leonardo.proposta.cartao.bloqueio.apiBloqueio;


import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.cartao.CartaoRepository;
import com.leonardo.proposta.cartao.StatusCartao;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ConsultarBloqueio {

    @Autowired
    Tracer tracer;

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    ApiBloqueioLegadoClient apiBloqueioLegadoClient;

    @Scheduled(fixedDelay = 30000)  //Tempo sempre em milisegundos!
    @Transactional
    private void Consultar() {

        List<Cartao> cartoesParaSeremAtualizados = cartaoRepository.findByStatusCartao(StatusCartao.PEDIDO_RECEBIDO);

        if (!cartoesParaSeremAtualizados.isEmpty()) {
            for (Cartao cartao : cartoesParaSeremAtualizados) {
                try {
                    ApiBloqueioDTO response = apiBloqueioLegadoClient.bloquear(cartao.getNumero(), new ApiBloqueioForm("proposta"));

                    if (response.getResultado().equals("BLOQUEADO"))
                        cartao.setStatusCartao(StatusCartao.BLOQUEADO);
                    cartaoRepository.save(cartao);
                    System.out.println(cartao.getId() + " bloqueado");
                    Span activeSpan = tracer.activeSpan().setBaggageItem("user.email", cartao.getProposta().getEmail());

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
