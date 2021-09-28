package com.leonardo.proposta.cartao.avisoViagem.apiAvisoViagem;

public class ApiAvisoDto {

    private String resultado;

    @Deprecated
    ApiAvisoDto(){
    }

    public ApiAvisoDto(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }



}
