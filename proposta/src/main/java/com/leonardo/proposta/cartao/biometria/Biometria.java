package com.leonardo.proposta.cartao.biometria;

import com.leonardo.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String digital;

    private LocalDateTime dataRegistro;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String digital, Cartao cartao) {
        this.digital = digital;
        this.cartao = cartao;
        dataRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getDigital() {
        return digital;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public Cartao getCartao() {
        return cartao;
    }
}


