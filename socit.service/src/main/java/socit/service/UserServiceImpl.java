package socit.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import socit.domain.entity.GENDER;
import socit.domain.entity.URLMassage;
import socit.domain.entity.User;
import socit.domain.repository.UserRepository;
import socit.service.pojo.*;
import socit.service.util.Mailer;
import socit.service.util.UploadFiles;
import socit.service.util.ValidatorAuthentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

@Service
@Log4j
public class UserServiceImpl implements UserService {
    @Autowired
    private Mailer mailer;

    @Autowired
    private URLMassageService urlMassageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidatorAuthentication validatorAuthenticate;

    @Autowired
    private UploadFiles uploadFile;

    @Override
    @Transactional
    public User getById(Integer id) {
        log.debug("User: id = " + id);
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public Serializable save(User user) {
        log.debug("User: firstName = " + user.getFirstName() + ", lastName = " + user.getLastName()
                + ", login = " + user.getLogin() + ", password = " + user.getPassword() + ", email = " + user.getEmail()
                + "status = " + user.getStatus() + ", authority = " + user.getAuthority());
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        log.debug("User: firstName = " + user.getFirstName() + ", lastName = " + user.getLastName()
                + ", login = " + user.getLogin() + ", password = " + user.getPassword() + ", email = " + user.getEmail()
                + "status = " + user.getStatus() + ", authority = " + user.getAuthority());
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void remove(User user) {
        log.debug("User: firstName = " + user.getFirstName() + ", lastName = " + user.getLastName()
                + ", login = " + user.getLogin() + ", password = " + user.getPassword() + ", email = " + user.getEmail()
                + "status = " + user.getStatus() + ", authority = " + user.getAuthority());
        userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        log.debug("Get all User");
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Boolean existsByLogin(String login) {
        log.debug("Login = " + login);
        return userRepository.existsByLogin(login);
    }

    @Override
    @Transactional
    public Boolean existsByEmail(String email) {
        log.debug("Email = " + email);
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public Boolean existsByPassword(String password) {
        log.debug("Password = " + password);
        return userRepository.existsByPassword(passwordEncoder().encode(password));
    }

    @Override
    @Transactional
    public User getByLogin(String login) {
        log.debug("Login = " + login);
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        log.debug("Email = " + email);
        return userRepository.findByEmail(email);
    }

    @Override
    public void registrationUser(Registrator registrator, HttpServletRequest request) {
        String email = registrator.getEmail();
        log.debug("Get validate");
        validatorAuthenticate.validate(registrator, validatorAuthenticate.getValidator());
        log.debug("Set User");
        User user = new User(registrator.getLogin(), passwordEncoder().encode(registrator.getPassword()),
                registrator.getFirstName(), registrator.getLastName(), email, false, "ROLE_USER",
                "/static/images/default_avatar.jpg");
        save(user);
        String url = getFullUrl(request, "/emailConfirmed/", email);
        log.debug("Full URL = " + url);
        log.debug("Set URLMassage");
        URLMassage urlMassage = new URLMassage(url, user);
        urlMassageService.save(urlMassage);
        log.debug("Send email");
        mailer.send(email, url, "emailConfirmedHtml.html");
    }

    @Override
    public void restorePassword(Emailer emailer, HttpServletRequest request) {
        log.debug("Get validate");
        validatorAuthenticate.validate(emailer, validatorAuthenticate.getValidator());
        String email = emailer.getEmail();
        User user = getByEmail(email);
        String url = getFullUrl(request, "/passwordRestore/", email);
        log.debug("Full URL = " + url);
        URLMassage urlMassage = new URLMassage(url, user);
        urlMassageService.save(urlMassage);
        mailer.send(email, url, "restorePassword.html");
    }

    @Override
    public void saveNewPassword(Passworder passworder, Integer userId) {
        validatorAuthenticate.validate(passworder, validatorAuthenticate.getValidator());
        User user = getById(userId);
        user.setPassword(passwordEncoder().encode(passworder.getPassword()));
        update(user);
    }

    public void savePassword(ChangerPassword changerPassword) {
        validatorAuthenticate.validate(changerPassword, validatorAuthenticate.getValidator());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordEncoder().encode(changerPassword.getPassword()));
        update(user);
    }

    @Override
    public Map<String, List<String>> getCalendarData() {
        Map<String, List<String>> collections = new LinkedHashMap<>();
        List<String> days = getCollection(1, 32);
        List<String> months = getCollection(1, 13);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        List<String> years = getCollection(currentYear - 100, currentYear - 9);
        for (int i = currentYear - 100; i < currentYear - 9; i++) {
            years.add(String.valueOf(i));
        }
        collections.put("days", days);
        collections.put("months", months);
        collections.put("years", years);
        return collections;

    }

    @Override
    public Map<String, String> getUserDate(User user) {
        Map<String, String> map = new LinkedHashMap<>();
        Calendar date = user.getDate();
        if (date != null) {
            map.put("day", String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
            map.put("month", String.valueOf(date.get(Calendar.MONTH) + 1));
            map.put("year", String.valueOf(date.get(Calendar.YEAR)));
        }
        return map;
    }

    @Override
    public List<String> getCollection(int startNumber, int finishNumber) {
        List<String> collection = new ArrayList<>();
        for (int i = startNumber; i < finishNumber; i++) {
            collection.add(String.valueOf(i));
        }
        return collection;
    }

    @Override
    public void setSetting(Settinger setting, User user, String gender, String[] day, String[] month, String[] year ) {
        validatorAuthenticate.validate(setting, validatorAuthenticate.getValidator());
        user.setFirstName(setting.getFirstName());
        user.setLastName(setting.getLastName());
        user.setLogin(setting.getLogin());
        user.setCity(setting.getCity());
        try {
            if (gender!=null) {
                user.setGENDER(GENDER.valueOf(gender));
            }
        } catch (IllegalArgumentException e) {
        }

        if (day != null && month != null && year != null) {
            String currentDay = getDate(day);
            String currentMounth = getDate(month);
            String currentYear = getDate(year);

            if (currentDay != null && currentMounth != null && currentYear != null) {
                Calendar calendar = new GregorianCalendar(Integer.valueOf(currentYear), Integer.valueOf(currentMounth) - 1, Integer.valueOf(currentDay));
                user.setDate(calendar);
            }
        }
        update(user);
    }

    @Override
    public String getDate(String[] day){
        String date = null;
        for (String s : day) {
            if (s != null) {
                date = s;
                break;
            }
        }
        return date;
    }

    @Override
    public void saveAvatar(MultipartFile part, User user){
        int random = (int) (Math.random() * 10000);
        String fileName = part.getOriginalFilename();
        if (fileName != null && fileName.length() > 0) {
            String filePath = uploadFile.getFullSavePath("userId_" + user.getId()) + File.separator +String.valueOf(random)+ "avatar";
            // Write to file
            try {
                byte[] bytes = part.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(filePath));
                stream.write(bytes);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setAvatar("/st/userId_" + user.getId() +"/"+String.valueOf(random)+"avatar");
            update(user);
        }
    }

    @Override
    public String getFullUrl(HttpServletRequest req, String host, String email) {
        log.debug("host = " + host + ", email = " + email);
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        return scheme + "://" + serverName +
                ":" + req.getServerPort() +
                host + passwordEncoder().encode(email);
    }

    @Override
    public Boolean isAuthenticate() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof User && ((User) principal).getStatus()) {
            log.debug("Authentication success");
            return true;
        } else {
            log.debug("Authentication failed");
            return false;
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            private final Md5PasswordEncoder md5 = new Md5PasswordEncoder();

            @Override
            public String encode(CharSequence rawPassword) {
                return md5.encodePassword(rawPassword.toString(), 1);
            }

            @Override
            @SuppressWarnings("PMD")
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                log.debug("Get encode password");
                return (md5.encodePassword(rawPassword.toString(), 1)).equals(encodedPassword);
            }
        };
    }

}
