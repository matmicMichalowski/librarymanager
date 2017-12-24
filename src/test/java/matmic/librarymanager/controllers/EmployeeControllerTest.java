package matmic.librarymanager.controllers;

import matmic.librarymanager.command.EmployeeCommand;
import matmic.librarymanager.model.Employee;
import matmic.librarymanager.repositories.EmployeeRepository;
import matmic.librarymanager.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {

    @Mock
    private Model model;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Authentication auth = new UsernamePasswordAuthenticationToken("employee@mail.com", null);
        SecurityContextHolder.getContext().setAuthentication(auth);


        controller = new EmployeeController(employeeService, employeeRepository);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void showEmployees() throws Exception {
        Set<Employee> employees = new HashSet<>();

        employees.add(new Employee());
        Employee employee = new Employee();
        employee.setId(1L);
        employees.add(employee);

        when(employeeService.getEmployees()).thenReturn(employees);

        ArgumentCaptor<Set<Employee>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = controller.showEmployees(model);

        assertEquals("employee/employeelist", viewName);
        verify(employeeService, times(1)).getEmployees();
        verify(model, times(1)).addAttribute(eq("employees"), argumentCaptor.capture());
        Set<Employee> controllerSet = argumentCaptor.getValue();
        assertEquals(2, controllerSet.size());

    }

    @Test
    public void changeEmployeeStatus() throws Exception {

        mockMvc.perform(post("/employee/list")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employee/list"));
    }

    @Test

    public void showEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setEmail("employee@mail.com");


        when(employeeRepository.findByEmail(any())).thenReturn(employee);

        mockMvc.perform(get("/employee/display"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employeedisplay"))
                .andExpect(model().attributeExists("employee"));
    }

    @Test
    public void updateEmployee() throws Exception {
        EmployeeCommand employee = new EmployeeCommand();
        employee.setId(3L);

        when(employeeService.findEmployeeCommandById(anyLong())).thenReturn(employee);

        mockMvc.perform(get("/employee/3/update/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employeeupdateform"))
                .andExpect(model().attributeExists("employee"));

    }

    @Test
    public void saveUpdatedDetails() throws Exception {

        EmployeeCommand employeeCommand = new EmployeeCommand();
        employeeCommand.setId(1L);

        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("firstName", "Tomas")
                .param("lastName", "Novy")
                .param("email", "tomas@mail.com")
                .param("mobile", "342-432-234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employee/display"));
    }

    @Test
    public void saveUpdatedDetailsEmptyField() throws Exception {

        EmployeeCommand employeeCommand = new EmployeeCommand();
        employeeCommand.setId(1L);

        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("firstName", "Tomas")
                .param("lastName", "Novy")
                .param("mobile", "342-432-234"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employeeupdateform"));
    }

    @Test
    public void registerNewEmployee() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registeremployee"))
                .andExpect(model().attributeExists("employee"));

    }

    @Test
    public void saveNewEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(2L);
        employee.setEmail("employee@mail.com");

        when(employeeService.findEmployeeByEmail(any())).thenReturn(null);

        when(employeeService.saveEmployee(any())).thenReturn(employee);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Tom")
                .param("lastName", "Mot")
                .param("mobile", "123-321-213")
                .param("password", "password")
                .param("email", "newemployee@mail.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

    }

    @Test
    public void saveNewEmployeeEmployeeExists() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setEmail("employee@mail.com");

        when(employeeService.findEmployeeByEmail(any())).thenReturn(employee);
        when(employeeService.saveEmployee(any())).thenReturn(employee);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("firstName", "Jon")
                .param("lastName", "Smith")
                .param("mobile", "123-321-213")
                .param("email", "employee@mail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("registeremployee"));

    }

    @Test
    public void deleteEmployee() throws Exception {

        mockMvc.perform(get("/employee/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employee/list"));

        verify(employeeService,   times(1)).deleteEmployee(anyLong());
    }

}