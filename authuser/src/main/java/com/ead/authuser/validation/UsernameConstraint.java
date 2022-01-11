package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
//classe que usará essa validação
@Constraint(validatedBy = UsernameConstraintImpl.class)
//tipos que irão aceitar essa validação // método e campo
@Target({ElementType.METHOD, ElementType.FIELD})
//executa em tempo de execução
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {

    String message() default "Invalid username";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
