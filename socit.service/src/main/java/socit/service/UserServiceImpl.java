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
import socit.service.util.MailerImpl;
import socit.service.util.Registrator;
import socit.service.util.ValidatorAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Service
@Log4j
public class UserServiceImpl implements UserService {
    @Autowired
    private MailerImpl mailer;

    @Autowired
    private URLMassageService urlMassageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidatorAuthentication validatorAuthenticate;

    @Override
    @Transactional
    public User getById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public Serializable save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    @Transactional
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void registrationUser(Registrator registrator, HttpServletRequest request) {
        String email = registrator.getEmail();
        validatorAuthenticate.validate(registrator, validatorAuthenticate.getValidator());
        User user = new User(registrator.getLogin(), passwordEncoder().encode(registrator.getPassword()),
                registrator.getFirstName(), registrator.getLastName(), email, false, "ROLE_USER");
        save(user);
        String url = getFullUrl(request, email);
        URLMassage urlMassage = new URLMassage(url, user);
        urlMassageService.save(urlMassage);
        mailer.send(email, url);
    }

    @Override
    public String getFullUrl(HttpServletRequest req, String email) {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        return scheme + "://" + serverName +
                ":" + req.getServerPort() +
                "/emailRegis/" + passwordEncoder().encode(email);
    }

    @Override
    public User getAuthentication() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("Get authentication");
        return (principal != null && principal instanceof User)? (User) principal :null;
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
                return (md5.encodePassword(rawPassword.toString(), 1)).equals(encodedPassword);
            }
        };
    }

}
