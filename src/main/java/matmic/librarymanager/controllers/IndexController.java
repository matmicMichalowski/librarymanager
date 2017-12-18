package matmic.librarymanager.controllers;


import matmic.librarymanager.services.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IndexController {


    private final LoanService loanService;

    public IndexController(LoanService loanService) {
        this.loanService = loanService;
    }


    @GetMapping("/dashboard")
    public String getDashboardPage(Model model){

        model.addAttribute("loans", loanService.getLoans());
        return "dashboard";
    }

}
