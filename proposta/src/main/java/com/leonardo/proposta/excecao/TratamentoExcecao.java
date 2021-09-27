package com.leonardo.proposta.excecao;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class TratamentoExcecao {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Erro> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        List<Erro> erros = new ArrayList<>();
        for (int i = 0; i < ex.getFieldErrors().size(); i++) {//melhorar esse for
            ex.getFieldErrors().get(i).getField();

            String campo = ex.getFieldErrors().get(i).getField();
            String mensagem = ex.getFieldErrors().get(i).getDefaultMessage();
            //String mensagem = ex.getFieldError().getDefaultMessage();
            erros.add(new Erro(campo, mensagem));
        }
        return erros;
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<Erro> registroDuplicadoException(RegistroDuplicadoException ex) {
        return List.of(new Erro(ex.getCampo(), ex.getMensagem()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Erro handleEntityNotFound(EntityNotFoundException ex) {
        String campo = "Id";
        String mensagem = ex.getMessage();
        return new Erro(campo, mensagem);

    }

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Erro handleRegraDeNegocioException(RegraNegocioException ex) {
        String campo = ex.getCampo();
        String mensagem = ex.getMensagem();
        return new Erro(campo, mensagem);
    }

    @ExceptionHandler(CartaoBloqueadoException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public Erro handleRegraDeNegocioException(CartaoBloqueadoException ex) {
        String campo = ex.getCampo();
        String mensagem = ex.getMensagem();
        return new Erro(campo, mensagem);
    }


}


