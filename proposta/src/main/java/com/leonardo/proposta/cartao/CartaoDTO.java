package com.leonardo.proposta.cartao;

import com.leonardo.proposta.proposta.*;

import javax.validation.constraints.*;
import java.math.*;
import java.time.*;

public class CartaoDTO {

    @NotBlank
    private String id;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotNull
    private BigDecimal limite;

    public CartaoDTO(LocalDateTime emitidoEm, BigDecimal limite) {
        this.emitidoEm = emitidoEm;
        this.limite = limite;
    }

    public Cartao toModel(Proposta proposta) {
        return new Cartao(emitidoEm,id,limite,proposta);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public BigDecimal getLimite() {
        return limite;
    }

}
