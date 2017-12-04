package matmic.librarymaneger.services;


import matmic.librarymaneger.model.Employee;

import java.util.Set;

public interface EmployeeService {
    Employee findEmployeeByEmail(String email);
    Employee saveEmployee(Employee employee);
    Set<Employee> getEmployees();
}
