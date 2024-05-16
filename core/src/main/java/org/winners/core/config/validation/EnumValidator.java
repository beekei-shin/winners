package org.winners.core.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private ValidEnum annotation;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Set<String> enumValues = Arrays.stream(this.annotation.enumClass().getEnumConstants()).map(String::valueOf).collect(Collectors.toSet());
        boolean result = enumValues.stream().anyMatch(enumValue -> enumValue.equals(value));

        if (!result) {
            String message = this.annotation.message();
            if (message.contains("{") && message.contains("}")) {
                message = messageSourceAccessor.getMessage(message.replace("{", "").replace("}", ""));
                message = message.replace("{values}", String.join(", ", enumValues));
            }
            context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        }

        return result;
    }

}
