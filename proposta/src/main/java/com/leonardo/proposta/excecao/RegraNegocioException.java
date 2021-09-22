package com.leonardo.proposta.excecao;

public class RegraNegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String campo;
    private String mensagem;

    public RegraNegocioException(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}


