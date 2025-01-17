package com.leonardo.proposta.proposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardo.proposta.cartao.*;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosClient;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosDTO;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosForm;
import com.leonardo.proposta.utils.Encrypt;
import com.leonardo.proposta.validacao.*;
import feign.FeignException;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull

    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;


    private StatusProposta status;

    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;


    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = Encrypt.encrypt(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    @Deprecated
    public Proposta(){}
    
    public Long getId() {
        return id;
    }

    public String getDocumento() {

        return Encrypt.decrypt(documento);
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }


    public boolean isUnique(PropostaRepository propostaRepository) {
        if(propostaRepository.findByDocumento(this.documento).isPresent()){
            return  false;
        }
        return true;
    }


    public StatusProposta getStatus() {
        return status;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public void adicionarCartão(Cartao cartao){
        if(this.cartao == null){
                this.cartao = cartao;
        }
    }

    public Cartao getCartao() {
        return cartao;
    }
}
