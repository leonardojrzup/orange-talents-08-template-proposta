package com.leonardo.proposta.cartao.avisoViagem;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class AvisoViagemDto {

    private String ultimosQuatroDigitosCartao;

    private String destino;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate terminoViagem;


    @Deprecated
    AvisoViagemDto() {
    }

    public AvisoViagemDto(AvisoViagem viagem) {
        this.ultimosQuatroDigitosCartao = viagem.getCartao().getNumero().substring(viagem.getCartao().getNumero().length() - 4);
        this.destino = viagem.getDestino();
        this.terminoViagem = viagem.getTerminoViagem();
    }

    public String getUltimosQuatroDigitosCartao() {
        return ultimosQuatroDigitosCartao;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getTerminoViagem() {
        return terminoViagem;
    }
}

