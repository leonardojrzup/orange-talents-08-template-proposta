package com.leonardo.proposta.excecao;

import org.springframework.validation.*;

public class Erro {
    private String campo;
    private String mensagem;


    public Erro(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public Erro(FieldError fieldError) {
        this.campo = fieldError.getField();
        this.mensagem = fieldError.getDefaultMessage();
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}

