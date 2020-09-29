package com.springboot.ibmmq.validation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CountryCodeValidator implements ConstraintValidator<CountryCode,String> {

    @Value("#{'${country.code.values}'.split(',')}")
    private List<String> countyCodes;

    public CountryCodeValidator() {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return countyCodes.contains(value);
    }
}


