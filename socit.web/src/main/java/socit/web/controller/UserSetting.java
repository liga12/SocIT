package socit.web.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
                                  @RequestParam(value = "error", required = false) String errorSetting) {
        ModelAndView modelAndView = new ModelAndView("setting");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("user", user);
        if (error != null) {
            modelAndView.addObject("error", error);
        }
        if (errorSetting != null) {
            modelAndView.addObject("errorSetting", errorSetting);
        }
        Map<String, String> userDate = userService.getUserDate(user);
        for (Map.Entry<String, String> entry : userDate.entrySet()) {
            modelAndView.addObject(entry.getKey(), entry.getValue());
        }
        Map<String, List<String>> collections = userService.getCalendarData();
        for (Map.Entry<String, List<String>> entry : collections.entrySet()) {
            modelAndView.addObject(entry.getKey(), entry.getValue());
        }
        if (user.getGENDER() != null) {
            modelAndView.addObject("gender", user.getGENDER().ordinal());
        }
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Settinger settinger = new Settinger(firstName, lastName, login, city);
        try {
            userService.setSetting(settinger, user, gender, day, month, year);
        } catch (RegistrationException e) {
            redirectAttributes.addFlashAttribute("errorSetting", e.getMessage());
            return "redirect:/user/setting";
        }
        return "redirect:/user/setting";
    }

    @RequestMapping(value = "/user/setting/changePassword")
    public String changeSetting(@RequestParam("old_password") String oldPassword,
                                @RequestParam("password") String password,
                                @RequestParam("password") String passwordConfirmation,
                                RedirectAttributes redirectAttributes) {
        ChangerPassword changerPassword = new ChangerPassword(oldPassword, password, passwordConfirmation);
        try {
            userService.savePassword(changerPassword);
        } catch (RegistrationException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/setting";
        }
        return "redirect:/user/setting";
    }
}
