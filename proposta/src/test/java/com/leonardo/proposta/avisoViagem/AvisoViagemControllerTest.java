package com.leonardo.proposta.avisoViagem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardo.proposta.cartao.Cartao;
import com.leonardo.proposta.cartao.CartaoRepository;
import com.leonardo.proposta.cartao.StatusCartao;
import com.leonardo.proposta.cartao.avisoViagem.AvisoViagemForm;
import com.leonardo.proposta.cartao.bloqueio.BloqueioForm;
import com.leonardo.proposta.proposta.Proposta;
import com.leonardo.proposta.proposta.PropostaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class AvisoViagemControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    CartaoRepository cartaoRepository;

    @Test
    public void cadastroComSucesso() throws Exception {
        Proposta proposta = new Proposta("70343430126", "leojunior1326@gmail.com", "Leonardo", "Rua dois", new BigDecimal(2500L));
        Cartao cartao = new Cartao(LocalDateTime.now(), "12345678910", new BigDecimal("1000"), proposta);

        cartaoRepository.save(cartao);
        proposta.adicionarCartão(cartao);
        propostaRepository.save(proposta);

        AvisoViagemForm viagem = new AvisoViagemForm("Anápolis", LocalDateTime.of(2021, 10, 21, 14, 30), "192.168.12.7", "Google Chrome");

        MockHttpServletRequestBuilder request = post("/cartoes/" + cartao.getId().toString() + "/viagens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(viagem));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void naoDeveCadastrarSemIP() throws Exception {
        Proposta proposta = new Proposta("70343430126", "leojunior1326@gmail.com", "Leonardo", "Rua dois", new BigDecimal(2500L));
        Cartao cartao = new Cartao(LocalDateTime.now(), "12345678", new BigDecimal("100"), proposta);

        proposta.adicionarCartão(cartao);
        propostaRepository.save(proposta);

        AvisoViagemForm viagem = new AvisoViagemForm("Anápolis", LocalDateTime.of(2021, 10, 21, 14, 30), "", "Google Chrome");

        MockHttpServletRequestBuilder request = post("/cartoes/" + cartao.getId().toString() + "/viagens")
                .locale(new Locale("pt", "BR"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(viagem));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("ipClient"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("não deve estar em branco"));


    }


    @Test
    public void naoDeveCadastrarSemDestino() throws Exception {
        Proposta proposta = new Proposta("70343430126", "leojunior1326@gmail.com", "Leonardo", "Rua dois", new BigDecimal(2500L));
        Cartao cartao = new Cartao(LocalDateTime.now(), "12345678", new BigDecimal("100"), proposta);

        proposta.adicionarCartão(cartao);
        propostaRepository.save(proposta);

        AvisoViagemForm viagem = new AvisoViagemForm("", LocalDateTime.of(2021, 10, 21, 14, 30), "192.168.12.7", "Google Chrome");
        MockHttpServletRequestBuilder request = post("/cartoes/" + cartao.getId().toString() + "/viagens")
                .locale(new Locale("pt", "BR"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(viagem));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())

                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("destinoViagem"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("não deve estar em branco"));


    }


    @Test
    public void naoDeveCadastrarDataDeVoltaInválida() throws Exception {
        Proposta proposta = new Proposta("70343430126", "leojunior1326@gmail.com", "Leonardo", "Rua dois", new BigDecimal(2500L));
        Cartao cartao = new Cartao(LocalDateTime.now(), "12345678", new BigDecimal("100"), proposta);

        proposta.adicionarCartão(cartao);
        propostaRepository.save(proposta);

        AvisoViagemForm viagem = new AvisoViagemForm("Brasilia", LocalDateTime.of(2021, 07, 21, 14, 30), "192.168.12.7", "Google Chrome");


        MockHttpServletRequestBuilder request = post("/cartoes/" + cartao.getId().toString() + "/viagens")
                .locale(new Locale("pt", "BR"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(viagem));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())

                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("terminoViagem"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("deve ser uma data futura"));


    }


    @Test
    public void naoDeveCadastrarSemUserAgent() throws Exception {
        Proposta proposta = new Proposta("70343430126", "leojunior1326@gmail.com", "Leonardo", "Rua dois", new BigDecimal(2500L));
        Cartao cartao = new Cartao(LocalDateTime.now(), "12345678", new BigDecimal("100"), proposta);

        proposta.adicionarCartão(cartao);
        propostaRepository.save(proposta);

        AvisoViagemForm viagem = new AvisoViagemForm("Anápolis", LocalDateTime.of(2021, 10, 21, 14, 30), "192.168.12.7", "");


        MockHttpServletRequestBuilder request = post("/cartoes/" + cartao.getId().toString() + "/viagens")
                .locale(new Locale("pt", "BR"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(viagem));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("userAgent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("não deve estar em branco"));

    }


    @Test
    public void naoDeveCadastrarViagemEmUmCartaoInexistente() throws Exception {
        BloqueioForm bloqueio = new BloqueioForm("192.168.7.12", "Mozilla Giroflex");

        MockHttpServletRequestBuilder request = post("/cartoes/63478324236472368/bloqueio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bloqueio));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("campo").value("Id"))
                .andExpect(MockMvcResultMatchers.jsonPath("mensagem").value("Id do cartão não encontrado no banco de dados"));
    }

    @Test
    public void naoDeveCadastrarViagemEmUmCartaoBloqueado() throws Exception {
        Proposta proposta = new Proposta("70343430126", "leojunior1326@gmail.com", "Leonardo", "Rua dois", new BigDecimal(2500L));
        Cartao cartao = new Cartao(LocalDateTime.now(), "12345678", new BigDecimal("100"), proposta);
        cartao.setStatusCartao(StatusCartao.BLOQUEADO);
        proposta.adicionarCartão(cartao);
        propostaRepository.save(proposta);

        BloqueioForm bloqueio = new BloqueioForm("192.168.7.12", "Mozilla Giroflex");

        MockHttpServletRequestBuilder request = post("/cartoes/" + cartao.getId().toString() + "/bloqueio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bloqueio));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("campo").value("Cartão"))
                .andExpect(MockMvcResultMatchers.jsonPath("mensagem").value("O cartão " + cartao.getId() + " se encontra no estado bloqueado ou há um pedido de bloqueio em andamento para o cartão"));
    }
}
