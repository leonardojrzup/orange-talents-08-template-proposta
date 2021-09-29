package com.leonardo.proposta.carteira;

public class CarteiraDigitalDto {
    private String ultimosQuatroDigitosCartao;
    private String ultimosQuatroNumerosId;
    private String email;
    private String modelo;

    @Deprecated
    public CarteiraDigitalDto() {
    }

    public CarteiraDigitalDto(CarteiraDigital carteiraDigital) {
        this.ultimosQuatroDigitosCartao = carteiraDigital.getCartao().getNumero().substring(carteiraDigital.getCartao().getNumero().length() - 4);
        this.ultimosQuatroNumerosId = carteiraDigital.getId().substring(carteiraDigital.getId().length() - 4);
        this.email = carteiraDigital.getEmail();
        this.modelo = carteiraDigital.getModeloCarteira().toString();
    }

    public String getUltimosQuatroDigitosCartao() {
        return ultimosQuatroDigitosCartao;
    }

    public String getUltimosQuatroNumerosId() {
        return ultimosQuatroNumerosId;
    }

    public String getEmail() {
        return email;
    }

    public String getModelo() {
        return modelo;
    }
}