package socit.web.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import socit.domain.entity.User;
import socit.service.UserService;
import socit.service.exception.RegistrationException;
import socit.service.pojo.ChangerPassword;
import socit.service.pojo.Settinger;

import java.util.List;
import java.util.Map;

@Controller
@Log4j
public class UserSetting {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/setting")
    public ModelAndView toSetting(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "errorSetting", required = false) String errorSetting) {
        log.debug("Request URL = /user/setting with parameters: error = " + error + ", errorSetting = " + errorSetting);
        ModelAndView modelAndView = new ModelAndView("setting");
        User user = userService.getUserByPrincpals();
        modelAndView.addObject("user", user);
        log.debug("Add attribute user ");
        if (error != null) {
            log.debug("Add attribute error =  " + error);
            modelAndView.addObject("error", error);
        }
        if (errorSetting != null) {
            log.debug("Add attribute errorSetting =  " + errorSetting);
            modelAndView.addObject("errorSetting", errorSetting);
        }
        log.debug("Get userService.getUserDate(user)");
        Map<String, String> userDate = userService.getUserDate(user);
        log.debug("Date = " + userDate);
        for (Map.Entry<String, String> entry : userDate.entrySet()) {
            modelAndView.addObject(entry.getKey(), entry.getValue());
            log.debug("Add attribute " + entry.getKey() + " =  " + entry.getValue());
        }
        log.debug("userService.getCalendarData()");
        Map<String, List<String>> collections = userService.getCalendarData();
        for (Map.Entry<String, List<String>> entry : collections.entrySet()) {
            modelAndView.addObject(entry.getKey(), entry.getValue());
            log.debug("Add attribute " + entry.getKey() + " =  " + entry.getValue());
        }
        if (user.getGENDER() != null) {
            modelAndView.addObject("gender", user.getGENDER().ordinal());
            log.debug("Add attribute gender =  " + user.getGENDER().ordinal());
        }
        log.debug("Load jsp = registration");
        return modelAndView;
    }

    @RequestMapping(value = "/user/setting/save")
    public String changeSetting(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("login") String login,
                                @RequestParam("city") String city,
                                @RequestParam(value = "gender", required = false) String gender,
                                @RequestParam(value = "day") String[] day,
                                @RequestParam(value = "month") String[] month,
                                @RequestParam(value = "year") String[] year,
                                RedirectAttributes redirectAttributes) {
        log.debug("Request URL = /user/setting/save with parameters: firstName = " + firstName + ", lastName = " + lastName +
                ", login = " + login + ", city = " + city + ", gender = " + gender + ", day = " + day
                + ", month = " + month + ", year = " + year);
        log.debug("Get userService.getUserByPrincpals()");
        User user = userService.getUserByPrincpals();
        log.debug("Set Settinger");
        Settinger settinger = new Settinger(firstName, lastName, login, city);
        try {
            log.debug("Get  userService.setSetting(settinger, user, gender, day, month, year)");
            userService.setSetting(settinger, user, gender, day, month, year);
        } catch (RegistrationException e) {
            redirectAttributes.addFlashAttribute("errorSetting", e.getMessage());
            log.debug("Return URL = /user/setting");
            return "redirect:/user/setting";
        }
        log.debug("Return URL = /user/setting");
        return "redirect:/user/setting";
    }

    @RequestMapping(value = "/user/setting/changePassword")
    public String changeSetting(@RequestParam("old_password") String oldPassword,
                                @RequestParam("password") String password,
                                @RequestParam("passwordConfirmation") String passwordConfirmation,
                                RedirectAttributes redirectAttributes) {
        log.debug("Request URL = /user/setting with parameters: oldPassword = " + oldPassword + ", password = " + password +
                ", passwordConfirmation = " + passwordConfirmation);
        log.debug("Set  ChangerPassword(" + oldPassword + ", " + password + " " + passwordConfirmation + ")");
        ChangerPassword changerPassword = new ChangerPassword(oldPassword, password, passwordConfirmation);
        try {
            log.debug("Get userService.savePassword(" + changerPassword + ");");
            userService.savePassword(changerPassword);
        } catch (RegistrationException e) {
            log.debug("Add attribute error = " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            log.debug("Return URL = /user/setting");
            return "redirect:/user/setting";
        }
        log.debug("Return URL = /user/setting");
        return "redirect:/user/setting";
    }

    @RequestMapping(value = "/user/setting/saveAvatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        log.debug("Request URL = /user/setting/saveAvatar with MultipartFile");
        log.debug("userService.getUserByPrincpals()");
        User user = userService.getUserByPrincpals();
        log.debug("userService.saveAvatar(MultipartFile, User);");
        userService.saveAvatar(file, user);
        log.debug("Return URL = /user/setting");
        return "redirect:/user/setting";
    }
}
