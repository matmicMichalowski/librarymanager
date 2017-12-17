package matmic.librarymaneger.controllers;

import matmic.librarymaneger.command.EmployeeCommand;
import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.repositories.EmployeeRepository;
import matmic.librarymaneger.services.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("employee/list")
    public String showEmployees(Model model){
        model.addAttribute("employees", employeeService.getEmployees());
        return "employee/employeelist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/employee/list")
    public String changeEmployeeStatus(@RequestParam("id") String id){
       employeeService.switchEmployeeStatus(Long.valueOf(id));
        return "redirect:/employee/list";
    }

    @GetMapping("/employee/display")
    public String showEmployee(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Employee employee = employeeRepository.findByEmail(username);
        model.addAttribute(employee);
        return "employee/employeedisplay";
    }

    @GetMapping("/employee/{id}/update/details")
    public String updateEmployee(@PathVariable String id, Model model){
        model.addAttribute("employee", employeeService.findEmployeeCommandById(Long.valueOf(id)));
        return "employee/employeeupdateform";
    }

    @PostMapping("/update")
    public String saveUpdatedDetails(@ModelAttribute("employee") EmployeeCommand employeeCommand){
        employeeService.updateEmployee(employeeCommand);
        return "redirect:/employee/display";
    }

    @GetMapping("/register")
    public String registerNewEmployee(Model model){
        model.addAttribute("employee", new Employee());
        return "registeremployee";
    }


    @PostMapping("/registration")
    public String saveNewEmployee(@ModelAttribute("employee") Employee employee) {

        Employee employeeToCreate = employeeService.findEmployeeByEmail(employee.getEmail());

            if(employeeToCreate == null){

                employeeService.saveEmployee(employee);

            }else{
                return null;
                // TODO check employee and give back message about taken email
            }
        return "redirect:/login";
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
