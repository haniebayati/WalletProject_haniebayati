package com.example.demo.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConscriptionValidator.class)
public @interface ConscriptionValidation {
    String message() default "Only males above 18 years old should specify conscription";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

