package matmic.librarymanager.services;

import matmic.librarymanager.command.EmployeeCommand;
import matmic.librarymanager.converter.EmployeeCommandToEmployee;
import matmic.librarymanager.converter.EmployeeToEmployeeCommand;
import matmic.librarymanager.model.Employee;
import matmic.librarymanager.model.rolemodel.EmployeeRole;
import matmic.librarymanager.model.rolemodel.Role;
import matmic.librarymanager.repositories.EmployeeRepository;
import matmic.librarymanager.repositories.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmployeeToEmployeeCommand employeeToEmployeeCommand;

    @Mock
    private EmployeeCommandToEmployee employeeCommandToEmployee;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private EmployeeServiceImpl service;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


         service = new EmployeeServiceImpl(employeeRepository, roleRepository, bCryptPasswordEncoder,
                employeeToEmployeeCommand, employeeCommandToEmployee);
    }

    @Test
    public void findEmployeeByEmail() throws Exception {
        Employee employee = new Employee();
        employee.setEmail("mail@mail.com");

        when(employeeRepository.findByEmail(any())).thenReturn(employee);

        Employee employeeFound = service.findEmployeeByEmail("mail@mail.com");

        assertNotNull("No employee found", employeeFound);
        verify(employeeRepository, times(1)).findByEmail(any());
        verify(employeeRepository, never()).findAll();

    }

    @Test
    public void findEmployeeCommandById() throws Exception {
        Employee employee = new Employee();
        employee.setId(2L);


        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

        EmployeeCommand command = new EmployeeCommand();
        command.setId(2L);

        when(employeeToEmployeeCommand.convert(any())).thenReturn(command);

        EmployeeCommand employeeFound = service.findEmployeeCommandById(2L);

        assertNotNull("Return null", employeeFound);
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, never()).findAll();
    }

    @Test
    public void getEmployees() throws Exception {
        Role testRole1 = new Role("TEST_ADMIN");
        Role testRole2 = new Role("TEST_EMPLOYEE");

        Employee employee = new Employee();
        employee.getEmployeeRoles().add(new EmployeeRole(employee, testRole1));
        employee.getEmployeeRoles().add(new EmployeeRole(employee, testRole2));

        Employee employee1 = new Employee();
        employee1.getEmployeeRoles().add(new EmployeeRole(employee1, testRole2));
        List<Employee> employees = new ArrayList<>();

        employees.add(employee);
        employees.add(employee1);

        when(employeeRepository.findAll()).thenReturn(employees);

        Set<Employee> employeeSet = service.getEmployees();

        assertEquals(1, employeeSet.size());
        verify(employeeRepository, times(1)).findAll();
        verify(employeeRepository, never()).findById(anyLong());
    }

    @Test
    public void saveNthEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setPassword("test");


        String password = bCryptPasswordEncoder.encode(employee.getPassword());
        Role role = new Role("TEST_EMPLOYEE");

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee());

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(password);

        employee.setPassword(password);

        when(employeeRepository.findAll()).thenReturn(employeeList);
        when(roleRepository.findByName(any())).thenReturn(role);
        when(employeeRepository.save(any())).thenReturn(employee);

        Employee savedEmployee = service.saveEmployee(employee);



        assertEquals(false, savedEmployee.isActive());
        assertEquals(1, employee.getEmployeeRoles().size());
        verify(employeeRepository, times(1)).findAll();
        verify(employeeRepository,never()).findById(anyLong());
        verify(roleRepository, times(1)).findByName(anyString());
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    public void saveAdminEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setPassword("test");


        String password = bCryptPasswordEncoder.encode(employee.getPassword());
        Role role = new Role("TEST_EMPLOYEE");
        Role role2 = new Role("TEST_ADMIN");


        List<Employee> employeeList = new ArrayList<>();

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(password);

        employee.setPassword(password);

        when(employeeRepository.findAll()).thenReturn(employeeList);
        when(roleRepository.findByName(any())).thenReturn(role);
        when(roleRepository.findByName(any())).thenReturn(role2);
        when(employeeRepository.save(any())).thenReturn(employee);

        Employee savedEmployee = service.saveEmployee(employee);



        assertEquals(true, savedEmployee.isActive());
        assertEquals(2, employee.getEmployeeRoles().size());
        verify(employeeRepository, times(1)).findAll();
        verify(employeeRepository,never()).findById(anyLong());
        verify(roleRepository, times(2)).findByName(any());
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    public void updateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(3L);

        EmployeeCommand commandToSave = new EmployeeCommand();
        commandToSave.setId(3L);

        when(employeeCommandToEmployee.convert(any())).thenReturn(employee);
        when(employeeRepository.save(any())).thenReturn(employee);
        when(employeeToEmployeeCommand.convert(any())).thenReturn(commandToSave);

        EmployeeCommand command = service.updateEmployee(commandToSave);

        assertNotNull(command);
        verify(employeeRepository, times(1)).save(any());
    }


    @Test
    public void findEmployeeByResetToken() throws Exception {
        Employee employee = new Employee();
        employee.setResetToken("test-token");
        Optional<Employee> employeeOptional = Optional.of(employee);

        when(employeeRepository.findByResetToken(anyString())).thenReturn(employeeOptional);

        Optional<Employee> optional = service.findEmployeeByResetToken(employee.getResetToken());

        assertNotNull(optional);
        verify(employeeRepository, times(1)).findByResetToken(anyString());
    }

    @Test
    public void loadUserByUsername() throws Exception {
    }

    @Test
    public void switchEmployeeStatus() throws Exception {
        Employee employee = new Employee();
        employee.setId(4L);
        employee.setActive(true);

        Optional<Employee> optional = Optional.of(employee);

        when(employeeRepository.findById(anyLong())).thenReturn(optional);

        service.switchEmployeeStatus(4L);

        assertEquals(false, employee.isActive());
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void deleteEmployee() throws Exception {
        Long toBeDeleted = 3L;

        service.deleteEmployee(toBeDeleted);

        verify(employeeRepository, times(1)).deleteById(anyLong());
    }

}