package matmic.librarymanager.controllers;

import matmic.librarymanager.command.EmployeeCommand;
import matmic.librarymanager.model.Employee;
import matmic.librarymanager.repositories.EmployeeRepository;
import matmic.librarymanager.services.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String saveUpdatedDetails(@Valid @ModelAttribute("employee") EmployeeCommand employeeCommand, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "employee/employeeupdateform";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (!username.equals(employeeCommand.getEmail())) {
            Authentication update = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials());
            SecurityContextHolder.getContext().setAuthentication(update);
        }
        employeeService.updateEmployee(employeeCommand);
        return "redirect:/employee/display";
    }

    @GetMapping("/register")
    public String registerNewEmployee(Model model){
        model.addAttribute("employee", new Employee());
        return "registeremployee";
    }


    @PostMapping("register")
    public String saveNewEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            return "registeremployee";
        }else {
            Employee employeeToCreate = employeeService.findEmployeeByEmail(employee.getEmail());

            if (employeeToCreate == null) {
                employeeService.saveEmployee(employee);
                return "redirect:/login";

            } else {
                model.addAttribute("emailTaken", "This email address is already registered. If you don't remember your password please use \"reset password\" option.");
                return "registeremployee";
            }
        }
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("employee/{id}/delete")
    public String deleteEmployee(@PathVariable String id){
        employeeService.deleteEmployee(Long.valueOf(id));
        return "redirect:/employee/list";
    }


    @GetMapping("/403")
    public String interesting(){
        return "403";
    }
}
