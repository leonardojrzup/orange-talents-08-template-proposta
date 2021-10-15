package com.leonardo.proposta.cartao.carteira;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardo.proposta.cartao.Cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CarteiraDigital {

    @Id
    private String id;

    @NotBlank
    @Email
    private String email;


    private ModeloCarteira modeloCarteira;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private Cartao cartao;

    @Deprecated
    public CarteiraDigital() {
    }

    public CarteiraDigital(String id, String email, ModeloCarteira modeloCarteira, Cartao cartao) {
        this.id = id;
        this.email = email;
        this.modeloCarteira = modeloCarteira;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ModeloCarteira getModeloCarteira() {
        return modeloCarteira;
    }

    public Cartao getCartao() {
        return cartao;
    }

}
