package socit.service;

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
import socit.service.util.Mailer;
import socit.service.util.Registrator;
import socit.service.util.ValidatorAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Autowired
    Mailer mailer;

    @Autowired
    URLMassageService urlMassageService;

    @Autowired
    private UserRepository userRepository;

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
        return  userRepository.findAll();
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
    public User getByLogin(String login){
        return userRepository.findByLogin(login);
    }
    @Override
    public void registrationUser(Registrator registrator, HttpServletRequest request) {
        String email = registrator.getEmail();
        new ValidatorAuthentication().validate(registrator, new ValidatorAuthentication().getValidator());
        User user = new User(registrator.getLogin(), passwordEncoder().encode(registrator.getPassword()),
                registrator.getFirstName(), registrator.getLastName(), email, false ,"ROLE_USER" );
        save(user);
        String url = getFullUrl(request, email);
        URLMassage urlMassage = new URLMassage(url, user);
        urlMassageService.save(urlMassage);
        mailer.send(email, url);
    }

    @Override
    public String getFullUrl(HttpServletRequest req, String email){
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        url.append(":").append(req.getServerPort());
        url.append("/emailRegis/").append(passwordEncoder().encode(email));
        return url.toString();
    }

    @Override
    public boolean isAutentificate() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal != null && principal instanceof User && ((User) principal).getStatus() == true) ? true : false;
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
