package matmic.librarymaneger.controllers;


import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.services.EmailService;
import matmic.librarymaneger.services.EmployeeServiceImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class LoginController {

    private final EmployeeServiceImpl employeeService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public LoginController(EmployeeServiceImpl employeeService, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @GetMapping(value={"/", "/login"})
//    public ModelAndView login(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("loginpage");
//        return modelAndView;
//    }

    @GetMapping({"/", "", "/login"})
    public String login(){
        return "loginpage";
    }

    @GetMapping(value="/home")
    public ModelAndView dashboard(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findEmployeeByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("/home");
        return modelAndView;
    }

    @GetMapping("/reset-request")
    public String displayForgottenPasswordPage(Model model){
        model.addAttribute("email");
        return "resetrequest";
    }

    @PostMapping("/reset-request")
    public String processRestPasswordRequest(Model model, @RequestParam("email") String employeeEmail, HttpServletRequest request){

        Employee employee = employeeService.findEmployeeByEmail(employeeEmail);

        if(employee == null){
            model.addAttribute("noSuchMail", "This email is not registered in our system.");

            return "/reset-request";
        }else{
            employee.setResetToken(UUID.randomUUID().toString());

            employeeService.saveEmployee(employee);

            String applicationUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

            SimpleMailMessage passwordResetMail = new SimpleMailMessage();

            passwordResetMail.setFrom("librarian.service.norply@gmail.com");
            passwordResetMail.setTo(employee.getEmail());
            passwordResetMail.setSubject("Password Reset Request");
            passwordResetMail.setText("Clink link below to reset your password:" + applicationUrl +
                    "/reset?token=" + employee.getResetToken());
            emailService.sendEmail(passwordResetMail);

            model.addAttribute("successMsg", "Password reset has been sent. Please check your mailbox and follow instructions in message.");
        }

        return "loginpage";
    }

    @GetMapping("/reset")
    public String passwordResetPage(Model model, @RequestParam("token") String token){

        Optional<Employee> employeeOptional = employeeService.findEmployeeByResetToken(token);

        if (employeeOptional.isPresent()){
            model.addAttribute("resetToken", token);
        }else{
            model.addAttribute("error", "Invalid password reset link.");
            return "loginpage";
        }

        return "resetpasswordform";
    }


    @PostMapping("/reset")
    public String setNewPassword(@RequestParam Map<String, String> requestParams, RedirectAttributes redir){

        Optional<Employee> employeeOptional = employeeService.findEmployeeByResetToken(requestParams.get("token"));

        if(employeeOptional.isPresent()){
            Employee resetPassEmployee = employeeOptional.get();

            resetPassEmployee.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

            resetPassEmployee.resetPasswordToken();

            employeeService.saveEmployee(resetPassEmployee);

            redir.addFlashAttribute("successMsg", "Password has been changed");

        }else{
            redir.addFlashAttribute("errorMsg", "This reset link is invalid please request for new.");
        }

        return "loginpage";

    }

//    @GetMapping(value="/registration")
//    public ModelAndView registration(Model model){
//
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("employee", new Employee());
//        modelAndView.setViewName("registration");
//        return modelAndView;
//    }

//    @PostMapping(value = "/registration")
//    public ModelAndView createNewUser(@Valid Employee employee, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        Employee employeeToCreate = employeeService.findEmployeeByEmail(employee.getEmail());
//        if (employeeToCreate != null) {
//            bindingResult
//                    .rejectValue("email", "error.user",
//                            "There is already a user registered with the email provided");
//        }
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("registration");
//        } else {
//            employeeService.saveEmployee(employee);
//            modelAndView.addObject("successMessage", "Employee has been registered successfully");
//            modelAndView.addObject("employee", new Employee());
//            modelAndView.setViewName("registration");
//        }
//        return modelAndView;
//    }





















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
