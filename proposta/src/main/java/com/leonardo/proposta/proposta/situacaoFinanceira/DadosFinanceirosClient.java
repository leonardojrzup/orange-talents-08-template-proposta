package com.leonardo.proposta.proposta.situacaoFinanceira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analise-financeira", url = "${feign.client.analise-financeira.url}")
public interface DadosFinanceirosClient {

        @PostMapping("/solicitacao")
        public DadosFinanceirosDTO consultar(@RequestBody  DadosFinanceirosForm form);
    }



