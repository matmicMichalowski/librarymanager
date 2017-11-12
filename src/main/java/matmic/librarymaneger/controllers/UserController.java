package matmic.librarymaneger.controllers;

import matmic.librarymaneger.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("userpanel/showlist")
    public String showUsersPanel(Model model){
        model.addAttribute("users", userService.getUsers());
        return "userpanel/userslist";
    }

    @RequestMapping("userpanel/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findById(new Long(id)));

        return "userpanel/show";
    }

    @RequestMapping("userpanel/{id}/userupdate")
    public String updateUser(@PathVariable String id, Model model){
        System.out.println("not found?");
        model.addAttribute("user", userService.findCommandById(Long.valueOf(id)));
        System.out.println("User found" + userService.findCommandById(Long.valueOf(id)));
        return "userpanel/userform";
    }

    @GetMapping
    @RequestMapping("userpanel/{id}/delete")
    public String deleteUserById(@PathVariable String id){
        userService.deleteById(Long.valueOf(id));
        return "index";

    }


}
