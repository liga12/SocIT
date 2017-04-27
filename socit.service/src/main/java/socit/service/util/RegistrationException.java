package socit.service.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class RegistrationException extends RuntimeException {

    @Getter
    @Setter
    private String errorMessage;

    RegistrationException(String errorMessage) {
        super(errorMessage);
        log.debug("Set message  = " +errorMessage);
    }


}
