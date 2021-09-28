package com.leonardo.proposta.cartao.biometria;


import com.leonardo.proposta.excecao.RegraNegocioException;
import com.leonardo.proposta.cartao.Cartao;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class BiometriaForm {

    @NotBlank
    private String digital;

    public BiometriaForm(String digital) {
        this.digital = digital;
    }

    @Deprecated
    public BiometriaForm() {
    }


    public Biometria toModel(Cartao cartao) {
        if (isBase64(this.digital)) {
            return new Biometria(this.digital, cartao);
        } else {
            throw new RegraNegocioException("Digital", "Digital não está no formato adequado");
        }
    }

    public Boolean isBase64(String someString) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            decoder.decode(someString);
        } catch (IllegalArgumentException ex) {
            // That string wasn't valid.
            return false;
        }
        return true;
    }


    public String getDigital() {
        return digital;
    }
}
