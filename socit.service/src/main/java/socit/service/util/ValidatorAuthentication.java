package socit.service.util;

import javax.validation.Validator;

public interface ValidatorAuthentication {

    void validate(Object object, Validator validator);

    Validator getValidator();
}
