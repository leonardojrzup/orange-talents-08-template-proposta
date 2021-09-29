package com.leonardo.proposta.carteira;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraDigitalRepository extends CrudRepository<CarteiraDigital, Long> {}

