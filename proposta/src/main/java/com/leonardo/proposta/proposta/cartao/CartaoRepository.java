package com.leonardo.proposta.proposta.cartao;

import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao,Long> {
}
