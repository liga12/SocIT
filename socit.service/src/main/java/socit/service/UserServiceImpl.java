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

    @Override
    public void savePassword(ChangerPassword changerPassword) {
        log.debug("Request values: ChangerPassword");
        log.debug("Get lidatorAuthenticate.validate(ChangerPassword, validatorAuthenticate.getValidator()");
        validatorAuthenticate.validate(changerPassword, validatorAuthenticate.getValidator());
        log.debug(getUserByPrincpals());
        User user = getUserByPrincpals();
        user.setPassword(passwordEncoder().encode(changerPassword.getPassword()));
        log.debug("User set password = "+passwordEncoder().encode(changerPassword.getPassword()));
        log.debug("update(User)");
        update(user);
    }

    @Override
    public Map<String, List<String>> getCalendarData() {
        Map<String, List<String>> collections = new LinkedHashMap<>();
        log.debug("Get getCollection(1,32)");
        List<String> days = getCollection(1, 32);
        log.debug("Get getCollection(1,13)");
        List<String> months = getCollection(1, 13);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        log.debug("Get getCollection(" + String.valueOf(currentYear - 100) + ", " + String.valueOf(currentYear - 9) + ")");
        List<String> years = getCollection(currentYear - 100, currentYear - 9);
//            for (int i = currentYear - 100; i < currentYear - 9; i++) {
//            years.add(String.valueOf(i));
//        }
        collections.put("days", days);
        collections.put("months", months);
        collections.put("years", years);
        return collections;

    }

    @Override
    public Map<String, String> getUserDate(User user) {
        log.debug("Request value user");
        Map<String, String> map = new LinkedHashMap<>();
        Calendar date = user.getDate();
        log.debug("Get date = " + date);
        if (date != null) {
            map.put("day", String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
            log.debug("Map put [ day," + String.valueOf(date.get(Calendar.DAY_OF_MONTH)) + " ]");
            map.put("month", String.valueOf(date.get(Calendar.MONTH) + 1));
            log.debug("Map put [ month," + String.valueOf(date.get(Calendar.MONTH) + 1) + " ]");
            map.put("year", String.valueOf(date.get(Calendar.YEAR)));
            log.debug("Map put [ year," + String.valueOf(date.get(Calendar.YEAR)) + " ]");
        }
        return map;
    }

    @Override
    public List<String> getCollection(int startNumber, int finishNumber) {
        log.debug("Request value: startNumber  = " + startNumber + ", finishNumber = " + finishNumber);
        List<String> collection = new ArrayList<>();
        for (int i = startNumber; i < finishNumber; i++) {
            collection.add(String.valueOf(i));
            log.debug("Add in collection value = " + String.valueOf(i));
        }
        return collection;
    }

    @Override
    public void setSetting(Settinger setting, User user, String gender, String[] day, String[] month, String[] year) {
        log.debug("Request values: Settinger, User, gender = " + gender + ", day = " + day + ", month = " + month
                + ", year =" + year);
        log.debug("Get validatorAuthenticate.validate(Settinger, validatorAuthenticate.getValidator())");
        validatorAuthenticate.validate(setting, validatorAuthenticate.getValidator());
        user.setFirstName(setting.getFirstName());
        log.debug("User set firstName = "+setting.getFirstName());
        user.setLastName(setting.getLastName());
        log.debug("User set lastName = "+setting.getLastName());
        user.setLogin(setting.getLogin());
        log.debug("User set login = "+setting.getLogin());
        user.setCity(setting.getCity());
        log.debug("User set city = "+setting.getCity());
        try {
            if (gender != null) {
                user.setGENDER(GENDER.valueOf(gender));
                log.debug("User set gender = "+GENDER.valueOf(gender));
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        }

        if (day != null && month != null && year != null) {
            String currentDay = getDate(day);
            log.debug("currentDay =  "+currentDay);
            String currentMounth = getDate(month);
            log.debug("currentMounth =  "+currentMounth);
            String currentYear = getDate(year);
            log.debug("currentYear =  "+currentYear);

            if (currentDay != null && currentMounth != null && currentYear != null) {
                Calendar calendar = new GregorianCalendar(Integer.valueOf(currentYear), Integer.valueOf(currentMounth) - 1, Integer.valueOf(currentDay));
                user.setDate(calendar);
                log.debug("User set date = "+calendar);
            }
        }
        log.debug("update(User)");
        update(user);
    }

    @Override
    public String getDate(String[] day) {
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
    public void saveAvatar(MultipartFile part, User user) {
        log.debug("Request parameters: MultipartFile, User");
        int random = (int) (Math.random() * 10000);
        String fileName = part.getOriginalFilename();
        log.debug("Fike name = "+fileName);
        if (fileName != null && fileName.length() > 0) {
            log.debug("uploadFile.getFullSavePath(userId"+user.getId()+")");
            String filePath = uploadFile.getFullSavePath("userId_" + user.getId()) + File.separator
                    + String.valueOf(random) + "avatar";
            log.debug("filePath = "+filePath);
            // Write to file
            try {
                byte[] bytes = part.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(filePath));
                stream.write(bytes);
                log.debug("File write "+filePath);
                stream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            log.debug("Set User avatar = /st/userId_" + user.getId() + "/" + String.valueOf(random) + "avatar");
            user.setAvatar("/st/userId_" + user.getId() + "/" + String.valueOf(random) + "avatar");
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

    @Override
    public User getUserByPrincpals() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("Get User from Principals with values: firstName = " + user.getFirstName() + ", lastName = " + user.getLastName()
                + ", email = " + user.getEmail() + ", login = " + user.getLogin() + ", password = " + user.getPassword()
                + ", status = " + user.getStatus() + ", authority = " + user.getAuthority() + ", avatar = " + user.getAvatar() +
                ", city = " + user.getCity() + ", gender = " + user.getGENDER() + ", date of birth = " + user.getDate());
        return user;
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
