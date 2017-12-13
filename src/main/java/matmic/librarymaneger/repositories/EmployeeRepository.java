package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{
    Employee findByEmail(String email);
    List<Employee> findAll();
    Employee findEmployeeById(Long id);
}

