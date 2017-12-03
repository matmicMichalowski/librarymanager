package matmic.librarymaneger.controllers;

import matmic.librarymaneger.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/show")
    public String showEmployees(Model model){
        model.addAttribute("employee", employeeService.getEmployees());
        return "admin/employeelist";
    }
}
