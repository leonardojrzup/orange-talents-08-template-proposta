package com.leonardo.proposta.validacao;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;



@ReportAsSingleViolation //
@Constraint(validatedBy =  CarteirasSuportadasValidator.class) // Classe validadora não necessaria, pois implementamos 2 anotações
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CarteirasSuportadas {

    String message() default "Carteira não é suportada pelo sistema, atualmente são suportadas carteiras dos modelos PAYPAL E SAMSUNGPAY";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
