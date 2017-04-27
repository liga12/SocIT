package socit.web.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import socit.domain.entity.Post;
import socit.domain.entity.User;
import socit.domain.repository.PostRepository;
import socit.service.PhotoPostService;
import socit.service.PostService;
import socit.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private PostRepository postRepository;

    @Transactional
    @RequestMapping(value = "/user/home")
    public ModelAndView toUserWall() {
        log.debug("request: /user/home");
        User userByAuthentication = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("Get user by authentication user: login = " + userByAuthentication.getLogin()
                + ", password = " + userByAuthentication.getPassword() + ", id = " + userByAuthentication.getId() + ","
                + " firstName = " + userByAuthentication.getFirstName() + ", lastName = " + userByAuthentication.getLastName()
                + ", email = " + userByAuthentication.getEmail() + ", status = " + userByAuthentication.getStatus()
                + ", authority = "+userByAuthentication.getAuthority());
        User user = userService.getById(userByAuthentication.getId());
        log.info("Get user by id = " + userByAuthentication.getId() + " ");
        ModelAndView modelAndView = new ModelAndView("user_wall");
        if (user.getStatus()) {
            modelAndView.addObject("user", user);

            List<Post> listPost = new ArrayList<>();
            List<Post> lists = user.getPosts();
            if (lists != null) {
                for (Post list : lists) {
                    if (list.getStatus()) {
                        listPost.add(list);
                    }
                }
            }
            Collections.reverse(listPost);
            modelAndView.addObject("list", listPost);
        } else {
            modelAndView.setViewName("/login");
            modelAndView.addObject("error", "You have not confirmed registration via email");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/uploadFile")
    public String uploadImage(@RequestParam("description") String description,
                              @RequestParam(value = "all", required = false) String[] allUser,
                              @RequestParam("file") MultipartFile[] myFile) {
        try {
            photoPostService.savePhotoPost(description, allUser, myFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/user/home";
    }

    @RequestMapping(value = "/post/delete")
    public String postDelete(@RequestParam(value = "id") Integer id) {
        postService.remove(id);
        return "redirect:/user/home";


    }
}
