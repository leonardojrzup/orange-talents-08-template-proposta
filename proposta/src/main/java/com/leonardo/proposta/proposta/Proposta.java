package com.leonardo.proposta.proposta;

import com.leonardo.proposta.validacao.*;

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

    public boolean isUnique(PropostaRepository propostaRepository) {
        if(propostaRepository.findByDocumento(this.documento).isPresent()){
            return  false;
        }
        return true;

    }
}
