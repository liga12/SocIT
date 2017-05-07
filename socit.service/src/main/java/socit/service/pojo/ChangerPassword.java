package socit.service.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangerPassword {

    @Getter
    @Setter
    @NotNull(message = "Old Password must be not null")
    @Size(min = 8, max = 248, message = "Old Password must be in the range from 8 to 248 characters")
    private String oldPassword;

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

    public ChangerPassword(String oldPassword, String password, String passwordConfirmation) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
}
