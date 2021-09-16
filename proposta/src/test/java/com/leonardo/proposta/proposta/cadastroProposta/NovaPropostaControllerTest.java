package com.leonardo.proposta.proposta.cadastroProposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardo.proposta.proposta.Proposta;
import com.leonardo.proposta.proposta.PropostaForm;
import com.leonardo.proposta.proposta.PropostaRepository;
import org.aspectj.weaver.ast.Var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class NovaPropostaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropostaRepository repository;


    @Test
    public void cadastroComSucesso() throws Exception {


        PropostaForm form = new PropostaForm("70343430126","leojunior1326@gmail.com","Leonardo", "Rua dois", new BigDecimal(2.500));

        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    public void naoDeveCadastrarSemDocumento() throws Exception {
        PropostaForm form = new PropostaForm("","leojunior@gmail.com","Leonardo", "Rua dois", new BigDecimal(2.500));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void naoDeveCadastrarDocumentoInvalido() throws Exception {
        PropostaForm form = new PropostaForm("111111111","leojunior1326@gmail.com","Leonardo", "Rua dois", new BigDecimal(2.500));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }



    @Test
    public void naoDeveCadastrarDocumentoDuplicado() throws Exception {

        Proposta proposta = new Proposta("16922635019","leojunior@gmail.com","Leonardo", "Rua dois", new BigDecimal(2.500));

    repository.save(proposta);


        PropostaForm form = new PropostaForm("16922635019","leojunior1326@gmail.com","Leonardo", "Rua dois", new BigDecimal(2.500));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isUnprocessableEntity());

    }



    @Test
    public void naoDeveCadastrarEmailInvalido() throws Exception {
        PropostaForm form = new PropostaForm("111111111","leo","Leonardo", "Rua dois", new BigDecimal(2.500));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }



    @Test
    public void naoDeveCadastrarEmailNulo() throws Exception {
        PropostaForm form = new PropostaForm("57984420041","","Leonardo", "Rua dois", new BigDecimal(2.500));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }


    @Test
    public void naoDeveCadastrarNomeBranco() throws Exception {
        PropostaForm form = new PropostaForm("75195930023","leojunior1326@gmail.com","", "Rua dois", new BigDecimal(2.500));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void naoDeveCadastrarEnderecoBranco() throws Exception {
        PropostaForm form = new PropostaForm("73657457038","leojunior1326@gmail.com","Leonardo", "", new BigDecimal(2.500));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void naoDeveCadastrarSalarioBranco() throws Exception {
        PropostaForm form = new PropostaForm("52797514050","leojunior1326@gmail.com","Leonardo", "Rua dois", new BigDecimal(0));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void naoDeveCadastrarSalarioNegativo() throws Exception {
        PropostaForm form = new PropostaForm("69544625003","leojunior1326@gmail.com","Leonardo", "Rua dois", new BigDecimal(-1L));


        MockHttpServletRequestBuilder request = post("/propostas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(form));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }


}
