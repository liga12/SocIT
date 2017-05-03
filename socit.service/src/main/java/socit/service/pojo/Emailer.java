package socit.service.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Emailer {

    private final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Getter
    @Setter
    @NotNull(message = "Email must be not null")
    @Size(min = 5, max = 64, message = "Email must be in the range from 5 to 64 characters")
    @Pattern(regexp = EMAIL_PATTERN,
            message = "Given email can not exist")
    private String email;

    public Emailer(String email) {
        this.email = email;
    }
}