package com.leonardo.proposta.proposta;

import org.springframework.data.repository.*;

import java.util.*;

public interface PropostaRepository extends CrudRepository <Proposta, Long> {

     Optional<Proposta> findByDocumento(String documento);


     List<Proposta> findByStatusAndCartaoIsNull(StatusProposta statusProposta);



}
