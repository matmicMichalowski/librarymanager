package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.rolemodel.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long>{
    ArrayList<EmployeeRole> findEmployeeRolesByEmployee_Id(Long id);
}
