package socit.service.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Log4j
public class Registrator {

    private final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Getter
    @Setter
    @NotNull(message = "Name must be not null")
    @Size(min = 1, max = 32, message = "Name must be in the range from 1 to 32 characters")
    private String firstName;


    @Getter
    @Setter
    @NotNull(message = "Surname must be not null")
    @Size(min = 1, max = 32, message = "Surname must be in the range from 1 to 32 characters")
    private String lastName;

    @Getter
    @Setter
    @NotNull(message = "Login must be not null")
    @Size(min = 1, max = 32, message = "Login must be in the range from 1 to 32 characters")
    private String login;

    @Getter
    @Setter
    @NotNull(message = "Email must be not null")
    @Size(min = 5, max = 64, message = "Email must be in the range from 5 to 64 characters")
    @Pattern(regexp = EMAIL_PATTERN,
            message = "Given email can not exist")
    private String email;

    @Getter
    @Setter
    @NotNull(message = "Password must be not null")
    @Size(min = 8, max = 248, message = "Password must be in the range from 8 to 248 characters")
    private String password;


    @Getter
    @Setter
    @NotNull(message = "passwordConfirmation must be not null")
    @Size(min = 8, max = 248, message = "PasswordConfirmation must be in the range from 8 to 248 characters")
    private String passwordConfirmation;

    public Registrator(String firstName, String lastName, String login, String email, String password,
                       String passwordConfirmation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        log.debug("Set values: firstName = "+firstName+", lastName = "+lastName+", email = "+email
                +", login = "+login+", password = "+password+", passwordConfirmation = "+passwordConfirmation);
    }
}