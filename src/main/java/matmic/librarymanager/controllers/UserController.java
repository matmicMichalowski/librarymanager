package matmic.librarymanager.controllers;


import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.model.User;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String saveOrUpdate(@Valid @ModelAttribute("user") UserCommand userCommand, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "user/userform";
        }

        User userToSave = userService.findUserByEmail(userCommand.getEmail());

        if (userToSave == null) {
            UserCommand savedUser = userService.saveUser(userCommand);
            return "redirect:/user/" + savedUser.getId() + "/display";

        }else if(userCommand.getId() != null){
            UserCommand savedUser = userService.saveUser(userCommand);
            return "redirect:/user/" +savedUser.getId() + "/display";

        } else {
            model.addAttribute("emailTaken", "This email address is already registered.");
            return "user/userform";
        }
    }


    @GetMapping("user/{id}/update/details")
    public String updateUser(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findUserCommandById(Long.valueOf(id)));
        return "user/userform";
    }

    @GetMapping("user/{id}/delete")
    public String deleteUserById(@PathVariable String id, Model model){
        if (!userService.deleteById(Long.valueOf(id))){
            model.addAttribute("activeLoanError", "This user still have active loans. Before deleting user please check on loans.");
            model.addAttribute("users", userService.getUsers());
            return "user/userlist";
        }
        return "redirect:/user/list";
    }

}
