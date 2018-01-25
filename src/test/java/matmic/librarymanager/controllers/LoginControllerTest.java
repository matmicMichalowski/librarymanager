package matmic.librarymanager.controllers;

import matmic.librarymanager.model.Employee;
import matmic.librarymanager.services.EmailService;
import matmic.librarymanager.services.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoginControllerTest {



    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmailService emailService;


    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private LoginController controller;


    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Authentication auth = new UsernamePasswordAuthenticationToken("employee@mail.com", null);

        SecurityContextHolder.getContext().setAuthentication(auth);

        controller = new LoginController(employeeService, emailService, bCryptPasswordEncoder);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void login() throws Exception {


        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginpage"));
    }

    @Test
    public void displayForgottenPasswordPage() throws Exception{

        mockMvc.perform(get("/reset-request"))
                .andExpect(status().isOk())
                .andExpect(view().name("resetrequest"))
                .andExpect(model().attributeExists("email"));
    }



    @Test
    public void processRestPasswordRequest() throws Exception {

        Employee employee = new Employee();
        employee.setId(2L);
        employee.setEmail("mail@mail.com");

        when(employeeService.findEmployeeByEmail(any())).thenReturn(employee);
        when(employeeService.resetPassword(any())).thenReturn(employee);


        mockMvc.perform(post("/reset-request").param("email",employee.getEmail())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("loginpage"))
                .andExpect(model().attributeExists("successMsg"));
    }

    @Test
    public void processRestPasswordRequestDenied() throws Exception {

        String email = "testmail@mailer.com";

        when(employeeService.findEmployeeByEmail(any())).thenReturn(null);

        mockMvc.perform(post("/reset-request").param("email", email)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reset-request"))
                .andExpect(model().attributeExists("noSuchMail"));
    }

    @Test
    public void passwordResetPage() throws Exception {

        String resetToken = "test-password-token";

        Employee employee = new Employee();
        employee.setResetToken(resetToken);

        when(employeeService.findEmployeeByResetToken(any())).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/reset").param("token", resetToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("resetpasswordform"))
                .andExpect(model().attributeExists("resetToken"));
    }


    @Test
    public void passwordResetPageError() throws Exception {

        mockMvc.perform(get("/reset").param("token", "false-token")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("loginpage"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void setNewPassword() throws Exception {

        Employee employee = new Employee();
        employee.setId(1L);

        Map<String, String> resetParams = new HashMap<>();
        resetParams.put("reset-token", "testtoken");
        resetParams.put("reset-password", "testpassword");

        when(employeeService.findEmployeeByResetToken(any())).thenReturn(Optional.of(employee));
        when(employeeService.resetPassword(any())).thenReturn(employee);

        mockMvc.perform(post("/reset").param("token", resetParams.get("reset-token"))
        .param("password", resetParams.get("reset-password")))
                .andExpect(status().isOk())
                .andExpect(view().name("loginpage"))
                .andExpect(model().attributeExists("successMsg"));
    }

    @Test
    public void setNewPasswordFailure() throws Exception {

        Map<String, String> resetParams = new HashMap<>();
        resetParams.put("reset-token", "testtoken");
        resetParams.put("reset-password", "testpassword");


        mockMvc.perform(post("/reset").param("token", resetParams.get("reset-token"))
        .param("password", resetParams.get("reset-password")))
                .andExpect(status().isOk())
                .andExpect(view().name("loginpage")).andDo(print())
                .andExpect(model().attributeExists("errorMsg"));

    }

}