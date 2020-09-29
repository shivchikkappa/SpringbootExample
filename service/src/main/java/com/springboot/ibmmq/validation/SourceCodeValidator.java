package com.springboot.ibmmq.validation;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class SourceCodeValidator implements ConstraintValidator<SourceCode,String> {

    @Value("#{'${source.valid.types}'.split(',')}")
    private List<String> sourceCodes;

    public SourceCodeValidator() {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return sourceCodes.contains(value);
    }
}
