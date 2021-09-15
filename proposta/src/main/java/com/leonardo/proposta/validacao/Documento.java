package com.leonardo.proposta.validacao;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR) // especifica que é composta por OR e não AND, então pode ser uma ou outra
@ReportAsSingleViolation //
@Constraint(validatedBy = { }) // Classe validadora não necessaria, pois implementamos 2 anotações
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Documento {

    String message() default "Documento não é válido como um CPF  CNPJ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}