package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.EmployeeCommand;
import matmic.librarymaneger.model.Employee;

import java.util.Set;

public interface EmployeeService {
    Employee findEmployeeByEmail(String email);
    EmployeeCommand saveEmployee(EmployeeCommand employeeCommand);
    Set<Employee> getEmployees();
}
