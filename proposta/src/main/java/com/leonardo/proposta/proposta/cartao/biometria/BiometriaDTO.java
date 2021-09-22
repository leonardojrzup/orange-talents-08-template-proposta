package com.leonardo.proposta.proposta.cartao.biometria;

import java.time.LocalDateTime;

public class BiometriaDTO {

    private Long id;
    private String digital;
    private LocalDateTime criadoEm;


    public BiometriaDTO(Biometria biometria) {
        this.id = biometria.getId();
        this.digital = biometria.getDigital().substring(0, biometria.getDigital().length() - 4);
        this.criadoEm = biometria.getDataRegistro();
    }

    public Long getId() {
        return id;
    }

    public String getDigital() {
        return digital;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
