package eu.virtusdevelops.ldmontage.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateInPastValidator.class)
public @interface DateInPast {
    int daysOffset() default 0;
    int hoursOffset() default 0;
    int minutesOffset() default 0;
    String message() default "Invalid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
