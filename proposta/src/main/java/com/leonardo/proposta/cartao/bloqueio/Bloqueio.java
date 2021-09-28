package com.leonardo.proposta.cartao.bloqueio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardo.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String ipCliente;

    @NotBlank
    private String userAgent;

    private LocalDateTime dataBloqueio;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private Cartao cartao;

    public Bloqueio(String ipCliente, String userAgent, Cartao cartao) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.dataBloqueio = LocalDateTime.now();
        this.cartao = cartao;
    }

    @Deprecated
    public Bloqueio() {
    }

    public Long getId() {
        return id;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getDataBloqueio() {
        return dataBloqueio;
    }

    public Cartao getCartao() {
        return cartao;
    }


}
