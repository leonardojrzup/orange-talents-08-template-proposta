package com.leonardo.proposta.cartao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardo.proposta.cartao.avisoViagem.AvisoViagem;
import com.leonardo.proposta.proposta.Proposta;
import com.leonardo.proposta.cartao.biometria.Biometria;
import com.leonardo.proposta.cartao.bloqueio.Bloqueio;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private StatusCartao statusCartao;

    @OneToOne(mappedBy = "cartao")
    @JsonIgnore
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Biometria> biometrias = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Bloqueio> bloqueios = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<AvisoViagem> viagens = new ArrayList<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(LocalDateTime emitidoEm, String numero, BigDecimal limite, Proposta proposta) {
        this.statusCartao = StatusCartao.DESBLOQUEADO;
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

    public List<Biometria> getBiometrias() {
        return biometrias;
    }

    public List<Bloqueio> getBloqueios() {
        return bloqueios;
    }

    public void setStatusCartao(StatusCartao statusCartao) {
        this.statusCartao = statusCartao;
    }

    public StatusCartao getStatusCartao() {
        return statusCartao;
    }

    public void adicionarBiometria(Biometria biometria) {
        this.biometrias.add(biometria);
    }

    public void adicionarBloqueio(Bloqueio bloqueio) {

        this.bloqueios.add(bloqueio);
    }

    public boolean isBloqueado() {
        if (statusCartao.equals(StatusCartao.BLOQUEADO) || statusCartao.equals(StatusCartao.PEDIDO_RECEBIDO)) {
            return true;
        }
        return false;
    }

    public void adicionarViagem(AvisoViagem viagem) {
        this.viagens.add(viagem);
    }

    public List<AvisoViagem> getViagens() {
        return viagens;
    }
}



