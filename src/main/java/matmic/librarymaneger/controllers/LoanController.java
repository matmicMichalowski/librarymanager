package matmic.librarymaneger.controllers;

import matmic.librarymaneger.commands.ItemCommand;
import matmic.librarymaneger.commands.LoanCommand;
import matmic.librarymaneger.commands.UserCommand;
import matmic.librarymaneger.services.ItemService;
import matmic.librarymaneger.services.LoanService;
import matmic.librarymaneger.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoanController {

    private final UserService userService;
    private final ItemService itemService;
    private final LoanService loanService;

    public LoanController(UserService userService, ItemService itemService, LoanService loanService) {
        this.userService = userService;
        this.itemService = itemService;
        this.loanService = loanService;
    }


    @GetMapping("loanpanel/user/{id}/newloan")
    public String searchForItemToBorrow(@PathVariable String id, Model model){
        model.addAttribute("items", itemService.getItems());
        model.addAttribute("user", userService.findById(Long.valueOf(id)));
        return "loanpanel/itemlist";
    }

    @GetMapping("loanpanel/{id}/item/{itemId}/newloan")
    public String showLoanForm(@PathVariable String id, @PathVariable String itemId,  Model model){
        model.addAttribute("item", itemService.findById(Long.valueOf(itemId)));
        model.addAttribute("user", userService.findById(Long.valueOf(id)));
        return "loanpanel/newloanform";
    }

    @PostMapping("loanpanel/user/{id}/item/{itemId}/saveloan")
    public String saveLoan(@PathVariable String id, @PathVariable String itemId){
        ItemCommand itemCommand = itemService.findCommandById(Long.valueOf(itemId));
        UserCommand userCommand = userService.findCommandById(Long.valueOf(id));
        LoanCommand loanCommand = new LoanCommand();
        loanCommand.setUser(userCommand);
        loanCommand.setItem(itemCommand);

        loanService.saveLoanCommand(loanCommand);

        return "redirect:/userpanel/" + id + "/show";
    }

    @GetMapping("loanpanel/user/{id}/loan/{loanId}/deleteloan")
    public String deleteLoan(@PathVariable String id){
        loanService.deleteLoanById(Long.valueOf(id));
        return "index";
    }
}
