package com.leonardo.proposta.proposta.cartao.bloqueio;

import com.leonardo.proposta.proposta.cartao.Cartao;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String ipCliente;

    @NotNull
    private String userAgent;

    private LocalDateTime dataBloqueio;

    @NotNull
    @ManyToOne
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
