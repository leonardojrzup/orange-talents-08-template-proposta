package com.leonardo.proposta.carteira.apiCarteiras;

public class ApiCarteiraDigitalForm {

    private String email;
    private String carteira;

    @Deprecated
    public ApiCarteiraDigitalForm() {
    }

    public ApiCarteiraDigitalForm(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
