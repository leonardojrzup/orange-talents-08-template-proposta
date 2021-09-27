package com.leonardo.proposta.proposta.cartao.bloqueio.apiBloqueio;

public class ApiBloqueioResponse {

    private String resultado;

    @Deprecated
    public ApiBloqueioResponse() {

    }


    public ApiBloqueioResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}