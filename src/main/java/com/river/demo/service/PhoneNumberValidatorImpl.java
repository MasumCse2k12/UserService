package com.river.demo.service;

import com.river.demo.utils.PhoneNumberValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidatorImpl implements ConstraintValidator<PhoneNumberValidator, String> {

    // ✅ Regex for Bangladeshi phone number
    // Matches: 017XXXXXXXX, 013XXXXXXXX, +88017XXXXXXXX etc.
    private static final String BD_PHONE_REGEX = "^(?:\\+8801[3-9]\\d{8}|01[3-9]\\d{8})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty if needed
        }
        return value.matches(BD_PHONE_REGEX);
    }
}

