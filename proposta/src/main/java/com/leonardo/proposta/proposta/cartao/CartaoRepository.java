package com.leonardo.proposta.proposta.cartao;

import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao,Long> {

    List<Cartao> findByStatusCartao(StatusCartao bloqueado);
}
