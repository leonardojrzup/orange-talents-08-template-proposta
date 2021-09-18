package com.leonardo.proposta.proposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosClient;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosDTO;
import com.leonardo.proposta.proposta.situacaoFinanceira.DadosFinanceirosForm;
import com.leonardo.proposta.validacao.*;
import feign.FeignException;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.lang.annotation.*;
import java.math.*;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Documento
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


    private StatusProposta Status;


    public StatusProposta getStatus() {
        return Status;
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
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
        return documento;
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

    public void setStatus(StatusProposta status) {
        Status = status;
    }

    public boolean isUnique(PropostaRepository propostaRepository) {
        if(propostaRepository.findByDocumento(this.documento).isPresent()){
            return  false;
        }
        return true;
    }

    public void verificaSituacaoFinanceira(DadosFinanceirosClient situacaoFinanceiraClient) throws JsonProcessingException {
        DadosFinanceirosForm request = new DadosFinanceirosForm(this);
        DadosFinanceirosDTO response;

         try {
             response = situacaoFinanceiraClient.consultar(request);
         }catch (FeignException exception) {
             ObjectMapper objectMapper = new ObjectMapper();
             response = objectMapper.readValue(exception.contentUTF8(), DadosFinanceirosDTO.class);
         }
        this.setStatus(StatusProposta.converter(response.getResultadoSolicitacao()));
    }
}
