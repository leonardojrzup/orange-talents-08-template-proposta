package com.leonardo.proposta.cartao.avisoViagem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardo.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String destino;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @NotNull
    @Future
    private LocalDate terminoViagem;

    private LocalDateTime instanteDeCriacao = LocalDateTime.now();

    @NotNull
    @ManyToOne
    @JsonIgnore
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(String destino, String ip, String userAgent, LocalDate terminoViagem, Cartao cartao) {
        this.destino = destino;
        this.ip = ip;
        this.userAgent = userAgent;
        this.terminoViagem = terminoViagem;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getDestino() {
        return destino;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDate getTerminoViagem() {
        return terminoViagem;
    }

    public LocalDateTime getInstanteDeCriacao() {
        return instanteDeCriacao;
    }

    public Cartao getCartao() {
        return cartao;
    }


}
