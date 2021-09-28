package com.leonardo.proposta.cartao.bloqueio;

import com.leonardo.proposta.excecao.CartaoBloqueadoException;
import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.cartao.StatusCartao;

import javax.validation.constraints.NotBlank;

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
            throw new CartaoBloqueadoException("Cartão", "O cartão " + cartao.getId() + " se encontra no estado bloqueado ou há um pedido de bloqueio em andamento para o cartão");
        } else {
            cartao.setStatusCartao(StatusCartao.PEDIDO_RECEBIDO);
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