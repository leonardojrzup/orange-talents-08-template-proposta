package com.leonardo.proposta.cartao.avisoViagem.apiAvisoViagem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aviso-viagens-legado", url = "${feign.client.cartoes.url}")
public interface ApiAvisoViagemLegadoClient {

    @PostMapping("/cartoes/{id}/avisos")
    public ApiAvisoDto informarAviso(@PathVariable("id") String id, @RequestBody ApiAvisoForm viagemForm);
}





