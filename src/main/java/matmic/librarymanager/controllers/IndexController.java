package matmic.librarymanager.controllers;


import matmic.librarymanager.repositories.ItemRepository;
import matmic.librarymanager.repositories.LoanRepository;
import matmic.librarymanager.repositories.UserRepository;
import matmic.librarymanager.services.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IndexController {


    private final LoanService loanService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final LoanRepository loanRepository;


    public IndexController(LoanService loanService, UserRepository userRepository, ItemRepository itemRepository, LoanRepository loanRepository) {
        this.loanService = loanService;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.loanRepository = loanRepository;
    }


    @GetMapping("/dashboard")
    public String getDashboardPage(Model model){


        model.addAttribute("users", userRepository.count());
        model.addAttribute("items", itemRepository.count());
        model.addAttribute("afterDeadline", loanRepository.countByBeforeDeadlineFalse());

        model.addAttribute("loans", loanService.getLoans());
        return "dashboard";
    }

    @GetMapping("/updateloans")
    public String updateLoanDeadlines(){
        loanService.updateLoansDeadline();
        return "redirect:/dashboard";
    }

}
