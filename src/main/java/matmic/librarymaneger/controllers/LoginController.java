package matmic.librarymaneger.controllers;


import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.services.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final EmployeeService employeeService;

    public LoginController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping({"/", "/login"})
    public String login(){

        return "login";
    }

    @GetMapping
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Employee employee = new Employee();
        modelAndView.addObject("employee", employee);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createNewUser(@Valid Employee employee, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Employee employeeToSave = employeeService.findEmployeeByEmail(employee.getEmail());
        if(employeeToSave != null){
            bindingResult.rejectValue("email", "error.employee", "Employee with this email already exist.");
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("registration");
        }else{
            employeeService.saveEmployee(employee);
            modelAndView.addObject("successMessage", "New Employee registered.");
            modelAndView.addObject("user", new Employee());
            modelAndView.setViewName("registration");
        }

        return modelAndView;
    }

    @GetMapping("/employee/home")
    public ModelAndView employeeHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findEmployeeByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + employee.getFirstName());
        modelAndView.setViewName("employee/home");
        return modelAndView;
    }

    @GetMapping("/login?logout")
    public String logout(){
        return "ok";
    }

}
