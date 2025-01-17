package com.leonardo.proposta.proposta;

import com.leonardo.proposta.utils.Encrypt;

import java.math.BigDecimal;

public class PropostaDTO {

        private Long id;
        private String documento;
        private String email;
        private String nome;
        private String endereco;
        private BigDecimal salario;
        private StatusProposta statusProposta;


        public PropostaDTO(String documento, String email, String nome, String endereco, BigDecimal salario, StatusProposta statusProposta) {
                this.documento = documento;
                this.email = email;
                this.nome = nome;
                this.endereco = endereco;
                this.salario = salario;
                this.statusProposta = statusProposta;
        }

        public PropostaDTO(Proposta propostaSalva){
                this.id = propostaSalva.getId();
                this.documento = propostaSalva.getDocumento();
                this.email = propostaSalva.getEmail();
                this.nome = propostaSalva.getNome();
                this.endereco = propostaSalva.getEndereco();
                this.salario = propostaSalva.getSalario();
                this.statusProposta = propostaSalva.getStatus();
        }

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

        public StatusProposta getStatusProposta() {
                return statusProposta;
        }
}
