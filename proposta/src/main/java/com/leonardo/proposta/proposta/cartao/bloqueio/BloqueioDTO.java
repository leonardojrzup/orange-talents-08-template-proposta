package com.leonardo.proposta.proposta.cartao.bloqueio;

import com.leonardo.proposta.proposta.cartao.Cartao;

import java.time.LocalDateTime;

public class BloqueioDTO {

    private String numeroCartao;

   private String ipRequisicao;

    private LocalDateTime bloqueadoEm;

    public BloqueioDTO(Bloqueio bloqueio) {
        this.numeroCartao =  bloqueio.getCartao().getNumero().replace( bloqueio.getCartao().getNumero().substring(0, bloqueio.getCartao().getNumero().length() -4), "*");
        this.ipRequisicao = bloqueio.getIpCliente();
        this.bloqueadoEm = bloqueio.getDataBloqueio();
    }


    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getIpRequisicao() {
        return ipRequisicao;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }
}

