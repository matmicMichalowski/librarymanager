package matmic.librarymanager.controllers;


import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.model.User;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.UserService;
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

    @RequestMapping("user/list")
    public String showUsersPanel(Model model){
        model.addAttribute("users", userService.getUsers());
        return "user/userlist";
    }

    @GetMapping("user/{id}/display")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findById(new Long(id)));
        model.addAttribute("items", itemService.getItems());
        return "user/userdisplay";
    }

    @GetMapping("user/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());

        return "user/userform";
    }

    @PostMapping
    @RequestMapping("saveuser")
    public String saveOrUpdate(@ModelAttribute("user") UserCommand userCommand){
        UserCommand savedUser = userService.saveUser(userCommand);

        return "redirect:/user/" + savedUser.getId() + "/display";
    }


    @GetMapping("user/{id}/update/details")
    public String updateUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findUserCommandById(Long.valueOf(id)));
        return "user/userform";
    }

    @GetMapping("user/{id}/delete")
    public String deleteUserById(@PathVariable String id){
        userService.deleteById(Long.valueOf(id));
        return "redirect:/user/list";

    }

}
