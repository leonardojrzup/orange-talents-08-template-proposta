package com.leonardo.proposta.proposta;

import com.leonardo.proposta.proposta.situacaoFinanceira.StatusSolicitacao;

public enum StatusProposta {
    NAO_ELEGIVEL,
    ELEGIVEL;

    public static StatusProposta converter(String statusSolicitacao) {
        if(statusSolicitacao.equals(StatusSolicitacao.COM_RESTRICAO.toString())){
            return NAO_ELEGIVEL;
        }else{
            return ELEGIVEL;
    }

}}