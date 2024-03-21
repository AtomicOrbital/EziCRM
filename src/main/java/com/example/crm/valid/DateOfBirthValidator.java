package com.example.crm.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateOfBirthValidator  implements ConstraintValidator<DateOfBirthContraint, LocalDate> {

    @Override
    public void initialize(DateOfBirthContraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        // Ngày sinh ko được lơn hơn ngày hiện tại
        return dateOfBirth.isBefore(LocalDate.now()) || dateOfBirth.isEqual(LocalDate.now());
    }
}
