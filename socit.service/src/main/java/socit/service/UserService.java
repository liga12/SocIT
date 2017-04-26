package socit.service;

import socit.domain.entity.User;
import socit.service.util.Registrator;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends BaseService<User, Integer> {

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);

    void registrationUser(Registrator registrator, HttpServletRequest request);

    String getFullUrl(HttpServletRequest req, String email);

    boolean isAutentificate();
}
