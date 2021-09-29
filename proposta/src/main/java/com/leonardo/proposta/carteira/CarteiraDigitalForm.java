package com.leonardo.proposta.carteira;

import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.excecao.RegraNegocioException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraDigitalForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @NotNull
    private ModeloCarteira modeloCarteira;

    public CarteiraDigitalForm(String email, ModeloCarteira modeloCarteira) {
        this.email = email;
        this.modeloCarteira = modeloCarteira;
    }

    public CarteiraDigital toModel(String id, Cartao cartao) {

        return new CarteiraDigital(id,email,modeloCarteira, cartao);
    }

    public String getEmail() {
        return email;
    }

    public ModeloCarteira getModeloCarteira() {
        return modeloCarteira;
    }

}
