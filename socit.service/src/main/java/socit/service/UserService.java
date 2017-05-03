package socit.service;

import socit.domain.entity.URLMassage;
import socit.domain.entity.User;
import socit.service.pojo.Emailer;
import socit.service.pojo.Passworder;
import socit.service.pojo.Registrator;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends BaseService<User, Integer> {

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);

    void registrationUser(Registrator registrator, HttpServletRequest request);

    String getFullUrl(HttpServletRequest request, String host, String email);

    User getByLogin(String login);

    User getByEmail(String email);

    Boolean isAuthenticate();

    void restorePassword(Emailer emailer, HttpServletRequest request);

    void saveNewPassword(Passworder passworder, Integer userId);
}
