package matmic.librarymaneger.controllers;


import matmic.librarymaneger.commands.EmployeeCommand;
import matmic.librarymaneger.converters.EmployeeToEmployeeCommand;
import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.services.EmployeeServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final EmployeeServiceImpl employeeService;
    private final EmployeeToEmployeeCommand employeeToEmployeeCmd;

    public LoginController(EmployeeServiceImpl employeeService, EmployeeToEmployeeCommand employeeToEmployeeCmd) {
        this.employeeService = employeeService;
        this.employeeToEmployeeCmd = employeeToEmployeeCmd;
    }

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employee", new Employee());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid EmployeeCommand employeeCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Employee employeeToCreate = employeeService.findEmployeeByEmail(employeeCommand.getEmail());
        if (employeeToCreate != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            employeeService.saveEmployee(employeeCommand);
            modelAndView.addObject("successMessage", "Employee has been registered successfully");
            modelAndView.addObject("employee", new Employee());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findEmployeeByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

//    @PostMapping("/employee/authentication")
//    public String authenticate(@PathVariable String email, Model model){
//        UserDetails user = employeeService.loadUserByUsername(email);
//        if(user == null){
//            return "index";
//        }
//        return "index";
//    }
//
//    @GetMapping({"/", "/login"})
//    public String login(){
//        return "login";
//    }
//
//
////    @GetMapping(value="/registration")
////    public ModelAndView registration(){
////        ModelAndView modelAndView = new ModelAndView();
////        Employee user = new Employee();
////        modelAndView.addObject("user", user);
////        modelAndView.setViewName("registration");
////        return modelAndView;
////    }
////
////    @PostMapping(value = "/registration")
////    public ModelAndView createNewUser(@Valid Employee employee, BindingResult bindingResult) {
////        ModelAndView modelAndView = new ModelAndView();
////        Employee userExists = employeeService.findEmployeeByEmail(employee.getEmail());
////        if (userExists != null) {
////            bindingResult
////                    .rejectValue("email", "error.user",
////                            "There is already a user registered with the email provided");
////        }
////        if (bindingResult.hasErrors()) {
////            modelAndView.setViewName("registration");
////        } else {
////           employeeService.saveEmployee(employee);
////            modelAndView.addObject("successMessage", "User has been registered successfully");
////            modelAndView.addObject("user", new Employee());
////            modelAndView.setViewName("registration");
////
////        }
////        return modelAndView;
////    }
//
//    @GetMapping("/registration")
//    public String registration(Model model){
//        model.addAttribute("employee", new EmployeeCommand());
//
//        return "/registration";
//    }
//
//    @PostMapping("submit")
//    public String saveNewUser(@ModelAttribute EmployeeCommand employeeCommand){
//        System.out.println(employeeCommand.getEmail());
//        EmployeeCommand employeeToSave = employeeService.saveEmployee(employeeCommand);
//
//            return "/login";
//    }
//
//    @GetMapping("/employee/home")
//    public ModelAndView employeeHome(){
//        System.out.println("tou jest tu?");
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Employee employee = employeeService.findEmployeeByEmail(auth.getName());
//        modelAndView.addObject("userName", "Welcome " + employee.getFirstName());
//        modelAndView.setViewName("employee/home");
//        return modelAndView;
//    }
//
//    @GetMapping("/login?logout")
//    public String logout(){
//        return "ok";
//    }

}
