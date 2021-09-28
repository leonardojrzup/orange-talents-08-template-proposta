package com.leonardo.proposta.cartao.avisoViagem.apiAvisoViagem;

import java.time.LocalDate;

public class ApiAvisoForm {

    private String destino;

    private LocalDate validoAte;


    @Deprecated
    ApiAvisoForm(){
    }

    public ApiAvisoForm(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
