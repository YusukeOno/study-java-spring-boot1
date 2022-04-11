package com.example.demo.validator;

import com.example.demo.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    private final SiteUserRepository userRepository;

    public UniqueLoginValidator() {
        this.userRepository = null;
    }

    @Autowired
    public UniqueLoginValidator(SiteUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository == null || userRepository.findByUsername(value) == null;
    }
}
