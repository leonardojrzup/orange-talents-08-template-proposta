package com.leonardo.proposta.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarteirasSuportadasValidator implements ConstraintValidator<CarteirasSuportadas,String> {
    @Override
    public void initialize(CarteirasSuportadas constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.equals("PAYPAL") || value.equals("SAMSUNGPAY")){
            return true;
        }
        return false;
    }
}
