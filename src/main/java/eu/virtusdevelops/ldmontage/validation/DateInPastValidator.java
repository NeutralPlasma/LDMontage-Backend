package eu.virtusdevelops.ldmontage.validation;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateInPastValidator implements ConstraintValidator<DateInPast, Date> {
    private int daysOffset;
    private int hoursOffset;
    private int minutesOffset;


    @Override
    public void initialize(DateInPast constraintAnnotation) {
        this.daysOffset = constraintAnnotation.daysOffset();
        this.hoursOffset = constraintAnnotation.hoursOffset();
        this.minutesOffset = constraintAnnotation.minutesOffset();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null){
            return false;
        }
        var current = new Date();
        LocalDateTime time = LocalDateTime.now().minusDays(daysOffset).minusHours(hoursOffset).minusMinutes(minutesOffset);

        LocalDateTime dateTimeToValidate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if(dateTimeToValidate.isBefore(time)){
            return true;
        }
        return false;
    }

}
