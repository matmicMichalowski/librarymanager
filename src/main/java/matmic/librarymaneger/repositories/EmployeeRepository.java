package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{
    Employee findByEmail(String email);
    List<Employee> findAll();
    Optional<Employee> findEmployeeById(Long id);
    Optional<Employee> findByResetToken(String resetToken);
}

