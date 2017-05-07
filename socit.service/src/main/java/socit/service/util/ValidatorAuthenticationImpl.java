package socit.service.util;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socit.service.UserService;
import socit.service.exception.RegistrationException;
import socit.service.pojo.ChangerPassword;
import socit.service.pojo.Emailer;
import socit.service.pojo.Passworder;
import socit.service.pojo.Registrator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
@Log4j
public class ValidatorAuthenticationImpl implements ValidatorAuthentication {

    @Autowired
    private UserService userService;

    @Override
    public void validate(Object object, Validator validator) {
        log.debug("Get validation Set ");
        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(object);

        for (ConstraintViolation<Object> cv : constraintViolations) {
            log.debug("Get validation message = "+cv.getMessage()+ " : "+cv.getPropertyPath()+" = "+cv.getInvalidValue());
            log.debug("throw new RegistrationException");
            throw new RegistrationException(String.valueOf(cv.getMessage()
                    + " : " + cv.getPropertyPath()) + " = " + cv.getInvalidValue());
        }

        if (object instanceof Registrator) {
            Registrator registrator = (Registrator) object;
            if (!registrator.getPassword().equals(registrator.getPasswordConfirmation())) {
                log.debug("throw new RegistrationException");
                throw new RegistrationException("Password not equal passwordConfirmation");
            }
            if (userService.existsByLogin(registrator.getLogin())) {
                log.debug("throw new RegistrationException");
                throw new RegistrationException("Login = " + registrator.getLogin() + " already exist");
            }
            if (userService.existsByEmail((registrator.getEmail()))) {
                log.debug("throw new RegistrationException");
                throw new RegistrationException("Email = " + registrator.getEmail() + " already exist");
            }
        }
        if (object instanceof Emailer){
            Emailer emailer = (Emailer) object;
            if (!userService.existsByEmail((emailer.getEmail()))) {
                log.debug("throw new RegistrationException");
                throw new RegistrationException("Email = " + emailer.getEmail() + " don't exist");
            }
        }
        if (object instanceof Passworder){
            Passworder passworder = (Passworder) object;
            if (!passworder.getPassword().equals(passworder.getPasswordConfirmation())) {
                log.debug("throw new RegistrationException");
                throw new RegistrationException("Password not equal passwordConfirmation");
            }
        }
        if (object instanceof ChangerPassword){
            ChangerPassword passworder = (ChangerPassword) object;
            if (!passworder.getPassword().equals(passworder.getPasswordConfirmation())) {
                log.debug("throw new RegistrationException");
                throw new RegistrationException("Password not equal passwordConfirmation");
            }
            if (!userService.existsByPassword(passworder.getOldPassword())) {
                log.debug("throw new RegistrationException");
                throw new RegistrationException("Password = " + passworder.getOldPassword() + " doesn't exist");
            }
        }
    }

    @Override
    public Validator getValidator() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        return vf.getValidator();
    }
}
