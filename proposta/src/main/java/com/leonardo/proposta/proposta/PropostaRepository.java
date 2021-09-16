package com.leonardo.proposta.proposta;

import org.springframework.data.repository.*;

import java.util.Optional;

public interface PropostaRepository extends CrudRepository <Proposta, Long> {

    public Optional<Proposta> findByDocumento(String documento);

}
