package matmic.librarymaneger.services;


import matmic.librarymaneger.command.EmployeeCommand;
import matmic.librarymaneger.model.Employee;

import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    Employee findEmployeeByEmail(String email);
    EmployeeCommand findEmployeeCommandById(Long id);
    Employee saveEmployee(Employee employee);
    EmployeeCommand updateEmployee(EmployeeCommand employeeCommand);
    Optional<Employee> findEmployeeByResetToken(String resetToken);
    Set<Employee> getEmployees();
    void switchEmployeeStatus(Long id);
    void deleteEmployee(Long id);
}
