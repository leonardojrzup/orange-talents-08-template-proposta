package com.leonardo.proposta.cartao.bloqueio.apiBloqueio;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Bloqueio-legado", url = "${feign.client.cartoes.url}")
public interface ApiBloqueioLegadoClient {

        @PostMapping("/cartoes/{id}/bloqueios")
        public ApiBloqueioDTO bloquear(@PathVariable("id") String id, @RequestBody ApiBloqueioForm sistemaResponsavel);
    }



