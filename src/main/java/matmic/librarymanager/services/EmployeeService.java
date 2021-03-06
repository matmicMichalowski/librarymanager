package matmic.librarymanager.services;


import matmic.librarymanager.command.EmployeeCommand;
import matmic.librarymanager.model.Employee;

import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    Employee findEmployeeByEmail(String email);
    EmployeeCommand findEmployeeCommandById(Long id);
    Employee saveEmployee(Employee employee);
    Employee resetPassword(Employee employee);
    EmployeeCommand updateEmployee(EmployeeCommand employeeCommand);
    Optional<Employee> findEmployeeByResetToken(String resetToken);
    Set<Employee> getEmployees();
    void switchEmployeeStatus(Long id);
    void deleteEmployee(Long id);
}
