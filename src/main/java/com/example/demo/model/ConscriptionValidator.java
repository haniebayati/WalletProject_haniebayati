package com.example.demo.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class ConscriptionValidator implements ConstraintValidator<ConscriptionValidation, Persons> {

    @Override
    public void initialize(ConscriptionValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Persons person, ConstraintValidatorContext context) {
        //check males above 18 years old
        if (person.getGender() == Gender.Male) {
            int age = Period.between(person.getBirthDate(), LocalDate.now()).getYears();
            if (age >= 18) {
                // If the age is above 18, the conscription value should not be null
                return person.getConscription() != null;
            }
        }
        // no need for conscription for other cases
        return true;
    }
}

