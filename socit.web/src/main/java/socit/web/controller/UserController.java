package socit.web.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import socit.domain.entity.Friend;
import socit.domain.entity.Post;
import socit.domain.entity.User;
import socit.service.FriendService;
import socit.service.PhotoPostService;
import socit.service.PostService;
import socit.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Log4j
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private PhotoPostService photoPostService;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendService friendService;

    @RequestMapping(value = "/user/home")
    public String toUserWall() {
        Integer userId = userService.getUserByPrincpals().getId();
        return "redirect:/user/id" + userId + "/home";
    }

    @Transactional
    @RequestMapping("/user/{id}/home")
    public ModelAndView toAnyWall(@PathVariable(value = "id") String id, HttpServletResponse response) throws IOException {
        String idUser = id.substring(2, id.length());
        User user;
        int userId;
        try {
            userId = Integer.valueOf(idUser);
            user = userService.getById(userId);
            if (user == null) {
                throw new NullPointerException();
            }
        } catch (NumberFormatException | NullPointerException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ModelAndView("redirect:/error");
        }
        ModelAndView modelAndView = new ModelAndView("user_wall");
        if (user.getStatus()) {
            modelAndView.addObject("user", user);
            String date = userService.formatUserDate(user);
            if (date != null) {
                modelAndView.addObject("date", date);
            }
            List<Post> listPost = userService.getUserPost(user);
            modelAndView.addObject("list", listPost);
        } else {
            modelAndView.setViewName("/login");
            modelAndView.addObject("error", "You have not confirmed registration via email");
        }
        List<Friend> friends = friendService.getFriendsByUser(user);
        Boolean friendStatus = false;
        for (Friend friend : friends) {
            if (friend.getFriend().equals(userService.getUserByPrincpals())) {
                friendStatus = true;
                break;
            }
        }
        ModelAndView modelAndView1 = friendStatus ?
                modelAndView.addObject("friendStatus", true) :
                modelAndView.addObject("friendStatus", false);


        return modelAndView;
    }

    @RequestMapping(value = "/uploadFile")
    public String uploadImage(@RequestParam("description") String description,
                              @RequestParam(value = "all", required = false) String[] allUser,
                              @RequestParam("file") MultipartFile[] myFile) {
        if (description != null || myFile[0].getSize() > 0) {
            try {
                photoPostService.savePhotoPost(description, allUser, myFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/user/home";
    }

    @RequestMapping(value = "/post/delete")
    public String postDelete(@RequestParam(value = "id") String id) {
        postService.deletePost(id);
        return "redirect:/user/home";
    }

    @RequestMapping(value = "/post/edit")
    public String postEdit(@RequestParam(value = "id") String id,
                           @RequestParam(value = "comment") String comment) {
        postService.editPost(id, comment);
        return "redirect:/user/home";
    }
}