package com.leonardo.proposta.proposta.cartoes;

import com.fasterxml.jackson.annotation.*;
import com.leonardo.proposta.proposta.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;
import java.time.*;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime emitidoEm;


    @NotBlank
    private String numero;

    @NotNull
    private BigDecimal limite;

    @OneToOne(mappedBy = "cartao")
    @JsonIgnore
    private Proposta proposta;

    @Deprecated
    public Cartao() {
    }

    public Cartao(LocalDateTime emitidoEm, String numero, BigDecimal limite, Proposta proposta) {
        this.emitidoEm = emitidoEm;
        this.numero = numero;
        this.limite = limite;
        this.proposta = proposta;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public Proposta getProposta() {
        return proposta;
    }
}





