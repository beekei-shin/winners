package org.winners.core.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Inherited
@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface ValidEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "{jakarta.validation.constraints.ValidEnum.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
