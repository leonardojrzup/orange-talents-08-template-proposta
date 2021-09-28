package com.leonardo.proposta.cartao.avisoViagem;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.cartao.StatusCartao;
import com.leonardo.proposta.excecao.CartaoBloqueadoException;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemForm {

    @NotBlank
    private String destinoViagem;

    @NotNull
    @Future
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate terminoViagem;


    @NotBlank
    private String ipClient;

    @NotBlank
    private String userAgent;

    @Deprecated
    AvisoViagemForm(){

    }

    public AvisoViagemForm(String destinoViagem, LocalDate terminoViagem, String ipClient, String userAgent) {
        this.destinoViagem = destinoViagem;
        this.terminoViagem = terminoViagem;
        this.ipClient = ipClient;
        this.userAgent = userAgent;
    }

    public AvisoViagem toModel(Cartao cartao) {
        if(cartao.getStatusCartao().equals(StatusCartao.BLOQUEADO) || cartao.getStatusCartao().equals(StatusCartao.PEDIDO_RECEBIDO))
            throw new CartaoBloqueadoException("Cartão","Cartao está no estado bloqueado ou existe um pedido de bloqueio para o cartão");

        return new AvisoViagem(destinoViagem,ipClient,userAgent,terminoViagem, cartao);
    }


    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getTerminoViagem() {
        return terminoViagem;
    }

    public String getIpClient() {
        return ipClient;
    }

    public String getUserAgent() {
        return userAgent;
    }
}


