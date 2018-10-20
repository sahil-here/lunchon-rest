package util;

import exception.LOException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidator {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    public static void validate(Object object) throws LOException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if(constraintViolations.size() !=0 ) {
            String errorMessage = "";
            for (ConstraintViolation<Object> constraintViolation : constraintViolations)
                errorMessage += constraintViolation.getPropertyPath() +" "+ constraintViolation.getMessage() + " & ";
            errorMessage = errorMessage.substring(0, errorMessage.length() - 3);
            throw new LOException(400,errorMessage);
        }
    }
}
