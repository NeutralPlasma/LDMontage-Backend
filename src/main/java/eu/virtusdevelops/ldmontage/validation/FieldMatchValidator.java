package eu.virtusdevelops.ldmontage.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final PropertyDescriptor firstObj = BeanUtils.getPropertyDescriptor(value.getClass(), firstFieldName); // fix this cuz not correct
            final PropertyDescriptor secondObj = BeanUtils.getPropertyDescriptor(value.getClass(), secondFieldName);


            assert firstObj != null;
            var firstValue = firstObj.getReadMethod().invoke(value);
            var secondValue = firstObj.getReadMethod().invoke(value);


            return firstValue.equals(secondValue);
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }
}