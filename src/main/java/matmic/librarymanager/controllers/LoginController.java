package matmic.librarymanager.controllers;


import matmic.librarymanager.converter.EmployeeToEmployeeCommand;
import matmic.librarymanager.model.Employee;
import matmic.librarymanager.services.EmailService;
import matmic.librarymanager.services.EmployeeServiceImpl;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class LoginController {

    private final EmployeeServiceImpl employeeService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeToEmployeeCommand employeeConverter;


    public LoginController(EmployeeServiceImpl employeeService, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder, EmployeeToEmployeeCommand employeeConverter) {
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.employeeConverter = employeeConverter;
    }


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
        model.addAttribute("email", new String());
        return "resetrequest";
    }

    @PostMapping("/reset-request")
    public String processRestPasswordRequest(Model model, @RequestParam("email") String employeeEmail, HttpServletRequest request){

        Employee employee = employeeService.findEmployeeByEmail(employeeEmail);

        if(employee == null){
            model.addAttribute("noSuchMail", "This email is not registered in our system.");

            return "redirect:/reset-request";
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
    public String setNewPassword(@RequestParam Map<String, String> requestParams, Model redir){

        Optional<Employee> employeeOptional = employeeService.findEmployeeByResetToken(requestParams.get("token"));

        if(employeeOptional.isPresent()){
            Employee resetPassEmployee = employeeOptional.get();

            resetPassEmployee.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

            resetPassEmployee.resetPasswordToken();

            employeeService.resetPassword(resetPassEmployee);

            redir.addAttribute("successMsg", "Password has been changed");

        }else{
            redir.addAttribute("errorMsg", "This reset link is invalid please request for new.");
        }

        return "loginpage";

    }
}
