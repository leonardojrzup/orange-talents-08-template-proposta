package com.leonardo.proposta.proposta.situacaoFinanceira;

import com.leonardo.proposta.proposta.Proposta;
import com.leonardo.proposta.validacao.Documento;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DadosFinanceirosForm {

    @NotNull
    @Documento
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    private Long idProposta;


    public DadosFinanceirosForm(String documento, String nome, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public DadosFinanceirosForm(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
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
