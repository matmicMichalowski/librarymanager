package matmic.librarymaneger.controllers;


import matmic.librarymaneger.command.LoanCommand;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.model.Loan;
import matmic.librarymaneger.model.enums.Availability;
import matmic.librarymaneger.services.ItemService;
import matmic.librarymaneger.services.LoanService;
import matmic.librarymaneger.services.UserService;
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


    @GetMapping("user/{id}/loan/newloan")
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


    @GetMapping("loan/user/{id}/item/{itemId}/newloan")
    public String showLoanForm(@PathVariable String id, @PathVariable String itemId,  Model model){
        model.addAttribute("item", itemService.findById(Long.valueOf(itemId)));
        model.addAttribute("user", userService.findById(Long.valueOf(id)));
        model.addAttribute("loan", new Loan());
        return "loanpanel/newloanform";
    }

    @PostMapping("saveloan")
    public String saveLoan(@ModelAttribute LoanCommand loanCommand){
        LoanCommand savedLoan = loanService.saveLoan(loanCommand);
        return "redirect:/user/" + savedLoan.getUserId() + "/userdisplay";
    }


//    @RequestMapping("loan/user/{userId}/item/{itemId}/saveloan")
//    public String saveLoan(@PathVariable String userId, @PathVariable String itemId){
//        System.out.println("do I start?" );
//        Item item = itemService.findItemById(Long.valueOf(itemId));
//        User user = userService.findUserById(Long.valueOf(userId));
//        Loan loan = new Loan();
//        loan.setUser(user);
//        loan.setItem(item);
//
//        loanService.saveLoan(loan);
//
//        return "redirect:/userpanel/" + user.getId() + "/show";
//    }

    @GetMapping("loanpanel/user/{userId}/loan/{loanId}/deleteloan")
    public String deleteLoan(@PathVariable String loanId, @PathVariable String userId){
        loanService.deleteLoanById(Long.valueOf(loanId));
        return "redirect:/userpanel/" + userId + "/show";
    }
}
