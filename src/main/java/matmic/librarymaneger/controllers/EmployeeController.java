package matmic.librarymaneger.controllers;

import matmic.librarymaneger.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("employee/show")
    public String showEmployees(Model model){
        model.addAttribute("employees", employeeService.getEmployees());
        return "adminpanel/employeelist";
    }

    @GetMapping("employee/{id}/show")
    public String switchEmployeeStatus(@PathVariable String id){
       employeeService.switchEmployeeStatus(Long.valueOf(id));
       return "redirect:/employee/show";
    }

    @GetMapping("employee/{id}/delete")
    public String deleteEmployee(@PathVariable String id){
        employeeService.deleteEmployee(Long.valueOf(id));
        return "redirect:/employee/show";
    }


    @GetMapping("/403")
    public String interesting(){
        return "403";
    }
}
