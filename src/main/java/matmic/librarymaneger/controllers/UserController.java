package matmic.librarymaneger.controllers;

import matmic.librarymaneger.commands.UserCommand;
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

    @RequestMapping("userpanel/newuser")
    public String newUser(Model model){
        model.addAttribute("user", new UserCommand());

        return "userpanel/userform";
    }

    @PostMapping
    @RequestMapping("userpanel")
    public String saveOrUpdate(@ModelAttribute UserCommand command){
        System.out.println("newUser userCmd ID" + command.getId());
        UserCommand savedUserCommand = userService.saveUserCommand(command);

        System.out.println(savedUserCommand.getId() + " saved user id");
        return "redirect:/userpanel/" + savedUserCommand.getId() + "/show";
    }


    @RequestMapping("userpanel/{id}/userupdate")
    public String updateUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findCommandById(Long.valueOf(id)));
        return "userpanel/userform";
    }

    @GetMapping("userpanel/{id}/delete")
    public String deleteUserById(@PathVariable String id){
        userService.deleteById(Long.valueOf(id));
        return "index";

    }

}
