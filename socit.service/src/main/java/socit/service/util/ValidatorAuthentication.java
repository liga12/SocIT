package socit.service.util;


import org.springframework.beans.factory.annotation.Autowired;
import socit.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidatorAuthentication {

    @Autowired
    UserService userService;

    public void validate(Object object, Validator validator) {
        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(object);

        for (ConstraintViolation<Object> cv : constraintViolations) {
            throw new RegistrationException(String.valueOf(cv.getMessage()
                    + " : " + cv.getPropertyPath()) + " = " + cv.getInvalidValue());
        }

        if (object instanceof Registrator) {
            Registrator registrator = (Registrator) object;

            if (!registrator.getPassword().equals(registrator.getPasswordConfirmation())) {
                throw new RegistrationException("Password not equal passwordConfirmation");
            }
            try {
                if (userService.existsByLogin(registrator.getLogin())) {
                    throw new RegistrationException("Login = " + registrator.getLogin() + " already exist");
                }
                if (userService.existsByEmail(registrator.getEmail())) {
                    throw new RegistrationException("Email = " + registrator.getEmail() + " already exist");
                }
            } catch (NullPointerException e) {
            }
        }
    }

    public Validator getValidator() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        return validator;
    }
}
