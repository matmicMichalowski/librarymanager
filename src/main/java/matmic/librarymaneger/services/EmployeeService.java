package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.EmployeeCommand;
import matmic.librarymaneger.model.Employee;

public interface EmployeeService {
    Employee findEmployeeByEmail(String email);
    EmployeeCommand saveEmployee(EmployeeCommand employeeCommand);
}
