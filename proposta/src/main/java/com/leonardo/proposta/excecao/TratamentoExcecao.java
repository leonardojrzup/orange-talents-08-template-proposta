package com.leonardo.proposta.excecao;


import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.util.*;

@RestControllerAdvice
public class TratamentoExcecao {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Erro> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        List<Erro> erros = new ArrayList<>();
        for (int i = 0; i < ex.getFieldErrors().size(); i++) {//melhorar esse for
        ex.getFieldErrors().get(i).getField();

            String campo =  ex.getFieldErrors().get(i).getField();
            String mensagem =  ex.getFieldErrors().get(i).getDefaultMessage();
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
}



