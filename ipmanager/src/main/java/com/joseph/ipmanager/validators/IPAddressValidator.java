package com.joseph.ipmanager.validators;

import org.apache.commons.validator.routines.InetAddressValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IPAddressValidator implements ConstraintValidator<IPValue, String> {

    @Override
    public boolean isValid(String ip, ConstraintValidatorContext constraintValidatorContext) {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        if(validator.isValidInet4Address(ip)){
            return true;
        }
        return false;
    }

}
