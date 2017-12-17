package matmic.librarymaneger.converter;

import matmic.librarymaneger.command.EmployeeCommand;
import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


public class EmployeeCommandToEmployeeTest {


    private static final Long ID = 1L;
    private static final String EMAIL = "employee@mail.com";
    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";
    private static final String MOBILE = "321-123-213";

    @Mock
    private EmployeeRepository repository;

    private EmployeeCommandToEmployee converter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new EmployeeCommandToEmployee(repository);
    }

    @Test
    public void convert() throws Exception {
        EmployeeCommand employeeCommand = new EmployeeCommand();
        employeeCommand.setId(ID);
        employeeCommand.setEmail(EMAIL);
        employeeCommand.setFirstName(FIRST_NAME);
        employeeCommand.setLastName(LAST_NAME);
        employeeCommand.setMobile(MOBILE);

        Employee employee = converter.convert(employeeCommand);

        assertNotNull(employee);
        assertEquals(ID, employee.getId());
        assertEquals(EMAIL, employee.getEmail());
        assertEquals(FIRST_NAME, employee.getFirstName());
        assertEquals(LAST_NAME, employee.getLastName());
        assertEquals(MOBILE, employee.getMobile());

    }

}