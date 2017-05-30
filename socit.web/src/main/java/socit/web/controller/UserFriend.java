package socit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import socit.domain.entity.Friend;
import socit.domain.entity.User;
import socit.domain.enums.FRIENDSTATUS;
import socit.service.FriendService;
import socit.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserFriend {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Transactional
    @RequestMapping(value = "/user/friends")
    public ModelAndView toFriends() {
        ModelAndView modelAndView = new ModelAndView("friends");
        modelAndView.addObject("user", userService.getUserByPrincpals());
        modelAndView.addObject
                ("friends", friendService.getFriendsByUser(userService.getUserByPrincpals()));
        List<Friend> applicationFriends = friendService.getFriendsByFriendAndFriendStatus
                (userService.getUserByPrincpals(), FRIENDSTATUS.WAIT);
        if (applicationFriends.size() > 0) {
            modelAndView.addObject("applicationsFriends", applicationFriends);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/user/searchFriends")
    public ModelAndView searchFriends(@RequestParam(value = "search") String search) {
        ModelAndView modelAndView = new ModelAndView("friends");
        User userByPrincipals = userService.getUserByPrincpals();
        List<Friend> friends = friendService.getFriendsByUser(userByPrincipals);
        List<User> users = userService.findFriends(search);
        if (search != null && !search.equals("")) {
            List<User> userSort = new ArrayList<>();
            List<Friend> friendSort = new ArrayList<>();
            int count = 0;
            for (User user : users) {
                count++;
                if (!user.getId().equals(userByPrincipals.getId())) {
                    count--;
                    for (Friend friend : friends) {
                        if (friend.getFriend().getId().equals(user.getId())) {
                            friendSort.add(friend);
                            count++;
                            break;
                        }
                    }
                }
                if (count == 0) {
                    userSort.add(user);
                }
                count = 0;
            }
            modelAndView.addObject("users", userSort);
            modelAndView.addObject("friends", friendSort);
        }
        modelAndView.addObject("user", userByPrincipals);
        return modelAndView;
    }

    @RequestMapping(value = "/user/addFriends")
    public String addFriends(@RequestParam(value = "id") String id,
                             @RequestParam(value = "idFriend", required = false) String idFriend) {
        friendService.addFriends(id, idFriend);
        return "redirect:/user/friends";
    }

    @RequestMapping(value = "/user/deleteFriends")
    public String deleteFriends(@RequestParam(value = "id") String id,
                                @RequestParam(value = "idFriend", required = false) String idFriend,
                                @RequestParam(value = "idUser", required = false) String idUser) {
        friendService.deleteFriends(id, idFriend, idUser);
        return "redirect:/user/friends";
    }

    @RequestMapping(value = "/user/confirmFriends")
    public String confirmFriend(@RequestParam(value = "id") String id) {
        friendService.confirmFriend(id);
        return "redirect:/user/friends";
    }

    @RequestMapping(value = "/user/rejectFriends")
    public String rejectFriend(@RequestParam(value = "id") String id) {
        friendService.rejectFriend(id);
        return "redirect:/user/friends";
    }
}
