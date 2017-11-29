package matmic.librarymaneger.services;

import matmic.librarymaneger.model.Employee;

public interface EmployeeService {
    Employee findEmployeeByEmail(String email);
    void saveEmployee(Employee employee);
}
