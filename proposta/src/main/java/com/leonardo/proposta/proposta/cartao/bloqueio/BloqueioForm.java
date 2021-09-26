package com.leonardo.proposta.proposta.cartao.bloqueio;

import com.leonardo.proposta.excecao.RegraNegocioException;
import com.leonardo.proposta.proposta.cartao.Cartao;
import com.leonardo.proposta.proposta.cartao.StatusCartao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BloqueioForm {

    @NotBlank
    private String ipCliente;

    @NotBlank
    private String userAgent;

    public BloqueioForm(String ipCliente, String userAgent) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    @Deprecated
    public BloqueioForm() {
    }

    public Bloqueio toModel(Cartao cartao) {

        if (cartao.isBloqueado()) {
            throw new RegraNegocioException("Cartao", "O cartão" + cartao.getId() + " se encontra no estado bloqueado");
        } else {
            cartao.setStatusCartao(StatusCartao.BLOQUEADO);
            return new Bloqueio(this.ipCliente, this.userAgent, cartao);
        }
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }
}