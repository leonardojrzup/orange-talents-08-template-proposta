package com.leonardo.proposta.carteira.apiCarteiras;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "Carteira-digital-legado", url = "${feign.client.cartoes.url}")
public interface ApiCateiraDigitalClient {

    @PostMapping("/cartoes/{id}/carteiras")
    public ApiCarteiraDigitalDto consultar(@PathVariable("id") String id, @RequestBody ApiCarteiraDigitalForm form);

}
