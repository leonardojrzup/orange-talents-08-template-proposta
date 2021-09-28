package com.leonardo.proposta.cartao.avisoViagem;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AvisoViagemDto {

    private String ultimosQuatroDigitosCartao;

    private String destino;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime terminoViagem;


    public AvisoViagemDto(AvisoViagem viagem) {
    this.ultimosQuatroDigitosCartao = viagem.getCartao().getNumero().substring( viagem.getCartao().getNumero().length() -4);
    this.destino = viagem.getDestino();
    this.terminoViagem = viagem.getTerminoViagem();
    }

    public String getUltimosQuatroDigitosCartao() {
        return ultimosQuatroDigitosCartao;
    }

    public void setUltimosQuatroDigitosCartao(String ultimosQuatroDigitosCartao) {
        this.ultimosQuatroDigitosCartao = ultimosQuatroDigitosCartao;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getTerminoViagem() {
        return terminoViagem;
    }

    public void setTerminoViagem(LocalDateTime terminoViagem) {
        this.terminoViagem = terminoViagem;
    }
}

