package socit.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import socit.domain.entity.URLMassage;
import socit.domain.entity.User;
import socit.domain.repository.UserRepository;
import socit.service.pojo.Emailer;
import socit.service.pojo.Passworder;
import socit.service.pojo.Registrator;
import socit.service.util.Mailer;
import socit.service.util.ValidatorAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

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
