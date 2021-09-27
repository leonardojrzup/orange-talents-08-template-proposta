package com.leonardo.proposta.proposta.cartao.bloqueio.apiBloqueio;

public class ApiBloqueioDTO {

    private String resultado;

    @Deprecated
    public ApiBloqueioDTO() {

    }


    public ApiBloqueioDTO(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}