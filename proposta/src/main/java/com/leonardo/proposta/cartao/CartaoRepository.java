package com.leonardo.proposta.cartao;

import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao,Long> {

    List<Cartao> findByStatusCartao(StatusCartao bloqueado);

    Optional<Cartao> findById(Long id);
}
