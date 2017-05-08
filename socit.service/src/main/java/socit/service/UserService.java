package socit.service;

import org.springframework.web.multipart.MultipartFile;
import socit.domain.entity.User;
import socit.service.pojo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User, Integer> {

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);

    Boolean existsByPassword(String password);

    void registrationUser(Registrator registrator, HttpServletRequest request);

    String getFullUrl(HttpServletRequest request, String host, String email);

    User getByLogin(String login);

    User getByEmail(String email);

    Boolean isAuthenticate();

    void restorePassword(Emailer emailer, HttpServletRequest request);

    void saveNewPassword(Passworder passworder, Integer userId);

    Map<String, List<String>> getCalendarData();

    Map<String, String> getUserDate(User user);

    List<String> getCollection(int startNumber, int finishNumber);

    void setSetting(Settinger setting, User user, String gender, String[] day, String[] month, String[] year);

    String getDate(String[] day);

    void saveAvatar(MultipartFile part, User user);

    void savePassword(ChangerPassword changerPassword);
}
