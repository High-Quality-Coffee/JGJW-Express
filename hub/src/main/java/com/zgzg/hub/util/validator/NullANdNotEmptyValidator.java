package com.zgzg.hub.util.validator;

import com.zgzg.hub.util.annotation.NullAndNotEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullANdNotEmptyValidator implements ConstraintValidator<NullAndNotEmpty, String> {


  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    if (s == null) {
      return true;
    }
    return !s.trim().isEmpty();
  }
}
