package com.leonardo.proposta.proposta.cartoes;


import com.leonardo.proposta.proposta.situacaoFinanceira.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cartoes", url ="${feign.client.cartoes.url}")

public interface CartaoClient {

    @PostMapping("/cartoes")
    public CartaoDTO consultar(@RequestBody  CartaoForm form);
}

