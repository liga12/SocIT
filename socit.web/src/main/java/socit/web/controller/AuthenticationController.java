package socit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import socit.domain.entity.URLMassage;
import socit.domain.entity.User;
import socit.service.URLMassageService;
import socit.service.UserService;
import socit.service.util.RegistrationException;
import socit.service.util.Registrator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private URLMassageService urlMassageService;

    private String data = "";


    @RequestMapping(value = "/")
    public String startPage() {
        return "redirect:/login";
    }


    @RequestMapping(value = "/login")
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView model = new ModelAndView("login");
        if (userService.isAuthenticate()) {
            return new ModelAndView("redirect:/user/home");
        }
        if (error != null) {
            model.addObject("error", "Invalid login or password");
        }
        return model;
    }

    @RequestMapping(value = "/registrationPage", method = RequestMethod.GET)
    public String toRegistrationPage() {
        if (userService.isAuthenticate()) {
            return "redirect:/user/home";
        }
        return "registration";
    }

    @RequestMapping(value = "/registration")
    public ModelAndView registration(@RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName,
                                     @RequestParam(value = "login", required = false) String login, @RequestParam(value = "password", required = false) String password,
                                     @RequestParam(value = "passwordConfirmation", required = false) String passwordConfirmation, @RequestParam(value = "email", required = false) String email,
                                     HttpServletRequest request)
            throws ServletException, IOException {
        if (userService.isAuthenticate()) {
            return new ModelAndView("redirect:/user/home");
        }
        ModelAndView modelAndView = new ModelAndView("onEmail");
        Registrator registrator = new Registrator(firstName, lastName, login, email, password,
                passwordConfirmation);
        try {
            userService.registrationUser(registrator, request);
            String[] emails = email.split("@");
            String emailClient = emails[1];
            modelAndView.addObject("host", "https://" + emailClient);
        }catch (RegistrationException e){
            data = e.getMessage();
            modelAndView.setViewName("registration");
            modelAndView.addObject("data", e.getMessage());
            data = null;
            return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping(value = "/emailRegis/*")
    public ModelAndView emailRegistration(HttpServletRequest request) {
        if (userService.isAuthenticate()) {
            return new ModelAndView("redirect:/user/home");
        }
        ModelAndView modelAndView = new ModelAndView("/login");
        URLMassage urlMassage = urlMassageService.getByUrl(urlMassageService.getURL(request));
        if (urlMassage != null) {
            User user = urlMassage.getUser();
            user.setStatus(true);
            userService.update(user);
            urlMassageService.remove(urlMassage);
            modelAndView.addObject("error", "You have confirmed the registration."
                    + " Enter the username and password to log on to the website");
        } else {
            modelAndView.addObject("error", "Link not found");
        }
        return modelAndView;
    }


}