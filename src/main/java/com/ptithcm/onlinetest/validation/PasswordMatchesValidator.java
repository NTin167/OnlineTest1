package com.ptithcm.onlinetest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {


    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {

    }


    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return false;
    }
}
