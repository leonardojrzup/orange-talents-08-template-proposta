package com.leonardo.proposta.cartao.bloqueio;

import java.time.LocalDateTime;

public class BloqueioDTO {

    private String numeroCartao;

    private String userAgent;

    private LocalDateTime bloqueadoEm;

    public BloqueioDTO(Bloqueio bloqueio) {
        this.numeroCartao = bloqueio.getCartao().getNumero().replace(bloqueio.getCartao().getNumero().substring(0, bloqueio.getCartao().getNumero().length() - 4), "*");
        this.bloqueadoEm = bloqueio.getDataBloqueio();
        this.userAgent = bloqueio.getUserAgent();
    }


    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }
}

