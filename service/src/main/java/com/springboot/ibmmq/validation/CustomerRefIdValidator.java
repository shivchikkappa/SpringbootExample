package com.springboot.ibmmq.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomerRefIdValidator implements ConstraintValidator<CustomerRefId,String> {

    @Override
    public void initialize(CustomerRefId constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !StringUtils.isEmpty(value);
    }
}
