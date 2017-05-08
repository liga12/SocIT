package socit.service.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import socit.domain.entity.GENDER;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Log4j
public class Settinger {

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
    @Size(max = 64, message = "City must be in the range from 1 to 64 characters")
    private String city;

    @Getter
    @Setter
    private Calendar date;

    @Getter
    @Setter
    private GENDER gender;

    public Settinger(String firstName, String lastName, String login, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.city = city;
        log.debug("Set values: firstName = " + firstName + ", lastName = " + lastName + ", login = " + login
                + ", city = " + city);
    }
}
