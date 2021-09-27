package com.leonardo.proposta.proposta.cartao.bloqueio.apiBloqueio;

public class ApiBloqueioForm {

    private String sistemaResponsavel ;

    @Deprecated
    public ApiBloqueioForm() {
    }

    public ApiBloqueioForm(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
