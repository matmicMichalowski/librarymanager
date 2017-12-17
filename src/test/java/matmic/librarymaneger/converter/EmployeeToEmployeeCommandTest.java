package matmic.librarymaneger.converter;

import matmic.librarymaneger.command.EmployeeCommand;
import matmic.librarymaneger.model.Employee;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class EmployeeToEmployeeCommandTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "employee@mail.com";
    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";
    private static final String MOBILE = "321-123-213";

    private EmployeeToEmployeeCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new EmployeeToEmployeeCommand();
    }

    @Test
    public void convert() throws Exception {
        Employee employee = new Employee();
        employee.setId(ID);
        employee.setEmail(EMAIL);
        employee.setFirstName(FIRST_NAME);
        employee.setLastName(LAST_NAME);
        employee.setMobile(MOBILE);

        EmployeeCommand employeeCommand = converter.convert(employee);

        assertNotNull(employeeCommand);
        assertEquals(ID, employeeCommand.getId());
        assertEquals(EMAIL, employeeCommand.getEmail());
        assertEquals(FIRST_NAME, employeeCommand.getFirstName());
        assertEquals(LAST_NAME, employeeCommand.getLastName());
        assertEquals(MOBILE, employeeCommand.getMobile());
    }

}