package matmic.librarymaneger.controllers;


import matmic.librarymaneger.model.User;
import matmic.librarymaneger.services.ItemService;
import matmic.librarymaneger.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final ItemService itemService;


    public UserController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @RequestMapping("user/userslist")
    public String showUsersPanel(Model model){
        model.addAttribute("users", userService.getUsers());
        return "user/userlist";
    }

    @RequestMapping("user/{id}/userdisplay")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findById(new Long(id)));
        model.addAttribute("items", itemService.getItems());
        return "user/userdisplay";
    }

    @RequestMapping("userpanel/newuser")
    public String newUser(Model model){
        model.addAttribute("user", new User());

        return "userpanel/userform";
    }

    @PostMapping
    @RequestMapping("saveuser")
    public String saveOrUpdate(@ModelAttribute User user){
        User savedUser = userService.saveUser(user);

        return "redirect:/user/" + savedUser.getId() + "/userdisplay";
    }


    @RequestMapping("userpanel/{id}/userupdate")
    public String updateUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findUserById(Long.valueOf(id)));
        return "userpanel/userform";
    }

    @GetMapping("userpanel/{id}/delete")
    public String deleteUserById(@PathVariable String id){
        userService.deleteById(Long.valueOf(id));
        return "redirect:/userpanel/userslist";

    }

}
