package com.leonardo.proposta.excecao;

public class RegistroDuplicadoException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String campo;
    private String mensagem;

    public RegistroDuplicadoException(String campo, String mensagem) {
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