package socit.service.util;

import lombok.Getter;
import lombok.Setter;

public class RegistrationException extends RuntimeException {

    @Getter
    @Setter
    private String errorMessage;

    public RegistrationException(String errorMessage) {
        super(errorMessage);
    }


}
