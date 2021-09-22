package com.leonardo.proposta.proposta.cartao;

public class CartaoForm {

    private String documento;

    private String nome;

    private Long idProposta;


    @Deprecated
    public CartaoForm(){
    }

    public CartaoForm(String documento, String nome, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
