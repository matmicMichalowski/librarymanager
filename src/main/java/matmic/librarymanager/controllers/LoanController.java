package matmic.librarymanager.controllers;


import matmic.librarymanager.command.LoanCommand;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.enums.Availability;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.LoanService;
import matmic.librarymanager.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


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


    @GetMapping("user/{id}/loan/new")
    public String searchForItemToBorrow(@PathVariable String id, Model model){
        List<Item> itemsToBorrow = new ArrayList<>();

        itemService.getItems().forEach(item -> {
            if(item.getIsAvailable() == Availability.AVAILABLE){
                itemsToBorrow.add(item);
            }
        });

        model.addAttribute("items", itemsToBorrow);
        model.addAttribute("user", userService.findById(Long.valueOf(id)));
        model.addAttribute("loan", new LoanCommand());
        return "user/newloan";
    }

    @PostMapping("saveloan")
    public String saveLoan(@ModelAttribute LoanCommand loanCommand){
        LoanCommand savedLoan = loanService.saveLoan(loanCommand);
        return "redirect:/user/" + savedLoan.getUserId() + "/display";
    }



    @GetMapping("/user/{userId}/loan/{loanId}/delete")
    public String deleteLoan(@PathVariable String loanId, @PathVariable String userId){
        loanService.deleteLoanById(Long.valueOf(loanId));
        return "redirect:/user/" + userId + "/display";
    }
}
